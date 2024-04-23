package com.blps.lab1.controllers;

import com.blps.lab1.dto.CreditOfferDTO;
import com.blps.lab1.service.CreditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/credit")
public class CreditController {
    private final CreditService creditService;

    public CreditController(CreditService creditService) {

        this.creditService = creditService;
    }

    @PostMapping(value = "/{id}/offer")
    public ResponseEntity<?> offer(@PathVariable(name = "id") Long id, @RequestBody CreditOfferDTO creditOfferDTO) {

        return creditService.creatOffer(id, creditOfferDTO);

    }

    @GetMapping(value = "/{id}/approved_cards")
    public ResponseEntity<?> approvedCards(@PathVariable(name = "id") Long id) {

        return creditService.getApprovedCards(id);

    }
}
