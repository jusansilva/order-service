package com.jusanmagno.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jusanmagno.order.model.Buyer;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {
}