package com.blps.lab1.controllers;

import com.blps.lab1.dto.DebitOfferDTO;
import com.blps.lab1.dto.UserDataDTO;
import com.blps.lab1.service.CommonService;
import com.blps.lab1.service.DebitService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/debit")
public class DebitController {

    private final DebitService debitService;
    private final CommonService commonService;

    public DebitController(DebitService debitService, CommonService commonService) {
        this.debitService = debitService;
        this.commonService = commonService;
    }

    @GetMapping(value = "/{id}/cards")
    public ResponseEntity<?> suitableCards(@PathVariable(name = "id") Long id) {

        return debitService.getCards(id);

    }

    @PostMapping(value = "/{id}/offer")
    public ResponseEntity<?> createOffer(@PathVariable(name = "id") Long id, @RequestBody DebitOfferDTO debitOfferDTO) {

        return debitService.creatOffer(id, debitOfferDTO);

    }

    @PostMapping(value = "{id}/fill_profile")
    public ResponseEntity<?> fillProfile (@PathVariable(name = "id") Long id, @RequestBody UserDataDTO userDataDTO){
        return commonService.toFillProfile(id,userDataDTO);
    }


}
