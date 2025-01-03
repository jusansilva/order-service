package com.jusanmagno.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jusanmagno.order.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByIdIn(List<Long> ids);
}
