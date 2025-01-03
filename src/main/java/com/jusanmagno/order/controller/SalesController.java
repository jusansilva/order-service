package com.jusanmagno.order.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jusanmagno.order.config.RabbitMQConfig;
import com.jusanmagno.order.model.Product;
import com.jusanmagno.order.model.Sales;
import com.jusanmagno.order.model.dto.request.AddProductSaleDTO;
import com.jusanmagno.order.model.dto.request.SalesCreateDTO;
import com.jusanmagno.order.repository.ProductRepository;
import com.jusanmagno.order.repository.SalesRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/sales")
public class SalesController {

    private final SalesRepository salesRepository;
    private final ProductRepository productRepository;
    private final RabbitTemplate rabbitTemplate;

    public SalesController(SalesRepository salesRepository, ProductRepository productRepository, RabbitTemplate rabbitTemplate) {
        this.salesRepository = salesRepository;
        this.productRepository = productRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping
    public Page<Sales> list(Pageable pageable) {
        Page<Sales> sales = salesRepository.findAll(pageable);
        return sales;
    }

    @GetMapping("/status/{status}")
    public Page<Sales> listByStatus(@PathVariable String status, Pageable pageable) {
        Page<Sales> sales = salesRepository.findByStatus(status, pageable);
        return sales;
    }

    @GetMapping("{saleId}")
    public Sales getSaleById(@PathVariable Long saleId) {
        Optional<Sales> optionalSale = salesRepository.findById(saleId);
        if (optionalSale.isPresent()) {
            return optionalSale.get();
        } else {
            throw new RuntimeException("Sale not found");
        }
    }

    

    @PutMapping("{saleId}/add-product")
    public Sales postMethodName(@RequestBody AddProductSaleDTO dto, @PathVariable Long saleId) {

        Optional<Sales> optionalSale = salesRepository.findById(saleId);
        if (!optionalSale.isPresent()) {
            throw new RuntimeException("Sale not found");
        }

        Sales sale = optionalSale.get();

        Map<Long, Integer> newProducts = dto.getProducts();

        List<Long> productIds = newProducts.keySet().stream().collect((Collectors.toList()));
        List<Product> products = productRepository.findByIdIn(productIds);

        List<Product> updatedProducts = sale.getProducts();
        updatedProducts.addAll(products.stream().map(product -> {
            product.setUnity(newProducts.get(product.getId()));
            return product;
        }).collect(Collectors.toList()));

        sale.setProducts(updatedProducts);
        sale.setUpdatedAt(LocalDateTime.now());

        double total = updatedProducts.stream().mapToDouble(product -> product.getUnity() * product.getPrice()).sum();

        sale.setTotal(total);

        return salesRepository.save(sale);
    }

    @DeleteMapping("{saleId}/remove-product")
    public Sales removeProductsFromSale(@RequestBody List<Long> productIds, @PathVariable Long saleId) {

        Optional<Sales> optionalSale = salesRepository.findById(saleId);
        if (!optionalSale.isPresent()) {
            throw new RuntimeException("Sale not found");
        }

        Sales sale = optionalSale.get();

        List<Product> updatedProducts = sale.getProducts().stream()
                .filter(product -> !productIds.contains(product.getId()))
                .collect(Collectors.toList());

        sale.setProducts(updatedProducts);
        sale.setUpdatedAt(LocalDateTime.now());

        double total = updatedProducts.stream().mapToDouble(product -> product.getUnity() * product.getPrice()).sum();
        sale.setTotal(total);

        return salesRepository.save(sale);
    }

    @PutMapping("{saleId}/update-status")
    public Sales updateSaleStatus(@PathVariable Long saleId, @RequestParam String status) {
        Optional<Sales> optionalSale = salesRepository.findById(saleId);
        if (!optionalSale.isPresent()) {
            throw new RuntimeException("Sale not found");
        }

        Sales sale = optionalSale.get();
        try {
            Sales.Status newStatus = Sales.Status.valueOf(status.toUpperCase());
            sale.setStatus(newStatus);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status value");
        }

        return salesRepository.save(sale);
    }

    @PostMapping
    public void sendSalesToQueue(@RequestBody SalesCreateDTO salesCreateDTO) {
        System.out.println("Sending sales data to queue: " + salesCreateDTO);

        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, salesCreateDTO);
    }

}
