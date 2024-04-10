package com.blps.lab1.controllers;

import com.blps.lab1.model.Cards;
import com.blps.lab1.model.CreditOffer;
import com.blps.lab1.repo.CardRepository;
import com.blps.lab1.repo.CreditRepository;
import com.blps.lab1.utils.mapper.CreditCardMapper;
import com.blps.lab1.utils.mapper.CreditOfferMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/approval")
public class ApprovingController {

    private final CreditRepository creditRepository;
    private final CreditOfferMapper creditOfferMapper;
    private final CreditCardMapper creditCardMapper;

    public ApprovingController(CreditRepository creditRepository,
                               CreditOfferMapper creditOfferMapper,
                               CreditCardMapper creditCardMapper) {
        this.creditRepository = creditRepository;
        this.creditOfferMapper = creditOfferMapper;
        this.creditCardMapper = creditCardMapper;
    }

    @GetMapping(value = "/{id}/info")
    public ResponseEntity<?> info(@PathVariable(value = "id") Long id) {
        CreditOffer creditOffer = creditRepository.findByUserId(id);
        return ResponseEntity.status(HttpStatus.OK).body(creditOfferMapper.toDTO(creditOffer));
    }

    @PostMapping(value = "/{id}/result")
    public ResponseEntity<?> result(@PathVariable(value = "id") Long id, @RequestBody List<Long> cardsId) {


        CreditOffer creditOffer = creditRepository.findByUserId(id);

        if (creditOffer.getReady())
            return ResponseEntity.status(HttpStatus.OK).body("Credit offer уже закрыт");

        creditOffer.getCards().removeIf(cards -> !cardsId.contains(cards.getId()));
        creditOffer.setApproved(!creditOffer.getCards().isEmpty());
        creditOffer.setReady(true);

        return ResponseEntity.status(HttpStatus.OK).body(creditRepository.save(creditOffer).getCards().stream().map(creditCardMapper::toDTO).collect(Collectors.toList()));


    }

}
