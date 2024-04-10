package com.blps.lab1.controllers;

import com.blps.lab1.dto.DebitOfferDTO;
import com.blps.lab1.model.Cards;
import com.blps.lab1.model.DebitOffer;
import com.blps.lab1.repo.DebitRepository;
import com.blps.lab1.repo.UserRepository;
import com.blps.lab1.service.DebitService;
import com.blps.lab1.utils.mapper.DebitCardMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/debit")
public class DebitController {

    private final DebitRepository debitRepository;
    private final UserRepository userRepository;
    private final DebitService debitService;
    private final DebitCardMapper debitCardMapper;

    public DebitController(DebitRepository debitRepository, UserRepository userRepository, DebitService debitService,
                           DebitCardMapper debitCardMapper) {
        this.debitRepository = debitRepository;
        this.userRepository = userRepository;
        this.debitService = debitService;
        this.debitCardMapper = debitCardMapper;
    }

    @GetMapping(value = "/{id}/cards")
    public ResponseEntity<?> suitableCards(@PathVariable(name = "id") Long id) {

        if (userRepository.findById(id).isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Нет пользователя с данным аутентификатором");

        if (debitRepository.findByUserId(id) == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("У данного пользователя нет запроса");

        DebitOffer debitOffer = debitRepository.findByUserId(id);
        List<Cards> cardsList = debitService.getCards(debitOffer);

        if (cardsList.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Нет карт с таким набором бонусов или целей");

        return ResponseEntity.ok(cardsList.stream().map(debitCardMapper::toDTO).toList());

    }

    @PostMapping(value = "/{id}/offer")
    public ResponseEntity<?> createOffer(@PathVariable(name = "id") Long id, @RequestBody DebitOfferDTO debitOfferDTO) {

        if (debitRepository.findByUserId(id) != null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь уже оставил запрос");

        DebitOffer debitOffer = debitService.DTOToDebitOffer(debitOfferDTO);
        debitOffer.setCard_user(userRepository.findById(id).get());

        return ResponseEntity.ok(debitService.debitOfferToDTO(debitRepository.save(debitOffer)));

    }


}
