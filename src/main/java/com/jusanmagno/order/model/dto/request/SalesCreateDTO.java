package com.jusanmagno.order.model.dto.request;

import java.io.Serializable;
import java.util.Map;

public class SalesCreateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long buyerId;
    private Map<Long, Integer> products;
    private String status;

    // Getters and setters
    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Map<Long, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Long, Integer> products) {
        this.products = products;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}