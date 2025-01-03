package com.jusanmagno.order.model.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProductCreateDTO {

    @NotBlank(message = "Params name is required")
    @Size(min= 2, message = "Params name is required 2 characters")
    private String name;

    private Integer unity;

    @NotBlank(message = "Params value is required")
    private Double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUnity() {
        return unity;
    }

    public void setUnity(Integer unity) {
        this.unity = unity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double value) {
        this.price = value;
    }

    
}
