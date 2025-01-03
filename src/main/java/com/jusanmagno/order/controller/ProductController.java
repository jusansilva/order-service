package com.jusanmagno.order.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jusanmagno.order.model.Product;
import com.jusanmagno.order.model.dto.request.ProductCreateDTO;
import com.jusanmagno.order.repository.ProductRepository;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping
    List<Product> listProducts(Pageable pageable) {
        return productRepository.findAll(pageable).getContent();
    }

    @GetMapping("{productId}")
    Product getById(@PathVariable Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product.get();
    }

    @PostMapping
    Product postProduct(@RequestBody ProductCreateDTO dto) {
        Product entity = new Product();
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setUnity(1);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        return productRepository.save(entity);
    }

    @PutMapping("{productId}")
    public Product updateProduct(@PathVariable Long productId, @RequestBody ProductCreateDTO dto) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (!optionalProduct.isPresent()) {
            throw new RuntimeException("Product not found");
        }

        Product product = optionalProduct.get();
        if (dto.getName() != null && !dto.getName().isEmpty()) {
            product.setName(dto.getName());
        }
        if (dto.getPrice() != null) {
            product.setPrice(dto.getPrice());
        }
        if (dto.getUnity() != null) {
            product.setUnity(dto.getUnity());
        }

        product.setUpdatedAt(LocalDateTime.now());

        return productRepository.save(product);
    }

    @DeleteMapping("{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (!optionalProduct.isPresent()) {
            throw new RuntimeException("Product not found");
        }

        productRepository.delete(optionalProduct.get());
    }

}
