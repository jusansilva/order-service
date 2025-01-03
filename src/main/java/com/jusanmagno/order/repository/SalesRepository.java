package com.jusanmagno.order.repository;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jusanmagno.order.model.Buyer;
import com.jusanmagno.order.model.Product;
import com.jusanmagno.order.model.Sales;

public interface SalesRepository extends JpaRepository<Sales, Long> {
        Page<Sales> findByStatus(String status, org.springframework.data.domain.Pageable pageable);

        Sales findByBuyerAndProductsInAndTotal(Buyer buyer, List<Product> products, Double total);
}
