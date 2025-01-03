package com.jusanmagno.order.controller;

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

import com.jusanmagno.order.model.Buyer;
import com.jusanmagno.order.model.dto.request.BuyerCreateDTO;
import com.jusanmagno.order.repository.BuyerRepository;

@RestController
@RequestMapping("/buyers")
public class BuyerController {

    @Autowired
    private BuyerRepository buyerRepository;

    @GetMapping
    List<Buyer> getBuyers(Pageable pageable) {
        return buyerRepository.findAll();
    }

    @GetMapping("{buyerId}")
    Buyer getBuyersByID(@PathVariable Long buyerId) {
        Optional<Buyer> optionalBuyer = buyerRepository.findById(buyerId);
        if (optionalBuyer.isPresent()) {
            return optionalBuyer.get();
        } else {
            throw new RuntimeException("Buyer not found");
        }
    }

    @PostMapping
    Buyer postBuyer(@RequestBody BuyerCreateDTO dto) {
        Buyer entity = new Buyer();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        return buyerRepository.save(entity);
    }

    @PutMapping("{buyerId}")
    public Buyer updateBuyer(@PathVariable Long buyerId, @RequestBody BuyerCreateDTO dto) {
        Optional<Buyer> optionalBuyer = buyerRepository.findById(buyerId);
        if (!optionalBuyer.isPresent()) {
            throw new RuntimeException("Buyer not found");
        }

        Buyer buyer = optionalBuyer.get();
        if (dto.getName() != null && !dto.getName().isEmpty()) {
            buyer.setName(dto.getName());
        }
        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
            buyer.setEmail(dto.getEmail());
        }

        return buyerRepository.save(buyer);
    }

    @DeleteMapping("{buyerId}")
    public void deleteBuyer(@PathVariable Long buyerId) {
        Optional<Buyer> optionalBuyer = buyerRepository.findById(buyerId);
        if (optionalBuyer.isPresent()) {
            buyerRepository.delete(optionalBuyer.get());
        } else {
            throw new RuntimeException("Buyer not found");
        }
    }
}
