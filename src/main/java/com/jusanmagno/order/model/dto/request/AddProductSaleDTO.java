package com.jusanmagno.order.model.dto.request;

import java.util.Map;

import jakarta.validation.constraints.NotBlank;

public class AddProductSaleDTO {

    @NotBlank(message = "")
    private Map<Long, Integer> products;

    public Map<Long, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Long, Integer> products) {
        this.products = products;
    }

}
