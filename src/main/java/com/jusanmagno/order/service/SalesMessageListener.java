package com.jusanmagno.order.service;

import com.jusanmagno.order.config.RabbitMQConfig;
import com.jusanmagno.order.model.Buyer;
import com.jusanmagno.order.model.Product;
import com.jusanmagno.order.model.Sales;
import com.jusanmagno.order.model.dto.request.SalesCreateDTO;
import com.jusanmagno.order.repository.BuyerRepository;
import com.jusanmagno.order.repository.ProductRepository;
import com.jusanmagno.order.repository.SalesRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalesMessageListener {

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(SalesCreateDTO entity) {
        Map<Long, Integer> prodList = entity.getProducts();

        Long buyerId = entity.getBuyerId();

        List<Long> productIds = prodList.keySet().stream().collect(Collectors.toList());

        List<Product> products = productRepository.findByIdIn(productIds);

        Optional<Buyer> buyer = buyerRepository.findById(buyerId);

        List<Product> productWithUnity = products.stream().map(product -> {
            product.setUnity(prodList.get(product.getId()));
            return product;
        }).collect(Collectors.toList());

        double total = productWithUnity.stream().mapToDouble(product -> product.getUnity() * product.getPrice()).sum();

        Sales sale = new Sales();
        buyer.ifPresent(sale::setBuyer);
        sale.setTotal(total);
        sale.setStatus(Sales.Status.CREATED);
        sale.setProducts(productWithUnity);
        sale.setCreatedAt(LocalDateTime.now());
        sale.setUpdatedAt(LocalDateTime.now());

        salesRepository.save(sale);
        
        System.out.println("Received sales data to queue: " + sale);
    }
}