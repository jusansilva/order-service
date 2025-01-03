package com.jusanmagno.order.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class BuyerCreateDTO {

    @NotBlank(message= "Params name is required!")
    @Size(min= 2, message = "Params name is required 2 characters")
    private String name;

    @NotBlank(message= "Params email is required!")
    @Email(message = "email is not valid")
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
}
