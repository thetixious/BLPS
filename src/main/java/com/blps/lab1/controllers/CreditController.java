package com.blps.lab1.controllers;

import com.blps.lab1.dto.CreditOfferDTO;
import com.blps.lab1.dto.UserDataDTO;
import com.blps.lab1.service.CommonService;
import com.blps.lab1.service.CreditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/credit")
public class CreditController {
    private final CreditService creditService;
    private final CommonService commonService;

    public CreditController(CreditService creditService, CommonService commonService) {

        this.creditService = creditService;
        this.commonService = commonService;
    }

    @PostMapping(value = "/{id}/offer")
    public ResponseEntity<?> offer(@PathVariable(name = "id") Long id, @RequestBody CreditOfferDTO creditOfferDTO) {

        return creditService.creatOffer(id, creditOfferDTO);

    }

    @GetMapping(value = "/{id}/approved_cards")
    public ResponseEntity<?> approvedCards(@PathVariable(name = "id") Long id) {

        return creditService.getApprovedCards(id);

    }

    @PostMapping(value = "{id}/fill_profile")
    public ResponseEntity<?> fillProfile(@PathVariable(name = "id") Long id, @RequestBody UserDataDTO userDataDTO){
        return commonService.toFillProfile(id,userDataDTO);
    }
}
