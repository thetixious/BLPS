package com.blps.lab1.controllers;

import com.blps.lab1.model.Cards;
import com.blps.lab1.model.CreditOffer;
import com.blps.lab1.repo.CardRepository;
import com.blps.lab1.repo.CreditRepository;
import com.blps.lab1.repo.UserRepository;
import com.blps.lab1.service.CreditService;
import com.blps.lab1.utils.mapper.CreditCardMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/credit")
public class CreditController {

    private final CreditRepository creditRepository;
    private final UserRepository userRepository;

    private final CreditService creditService;
    private final CreditCardMapper creditCardMapper;

    public CreditController(CreditRepository creditRepository, UserRepository userRepository, CreditService creditService,
                            CreditCardMapper creditCardMapper) {
        this.creditRepository = creditRepository;
        this.userRepository = userRepository;
        this.creditService = creditService;
        this.creditCardMapper = creditCardMapper;
    }

    @PostMapping(value = "/{id}/offer")
    public ResponseEntity<?> offer(@PathVariable(name = "id") Long id, @RequestBody CreditOffer creditOffer) {

        if (userRepository.findById(id).isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Нет пользователя с данным id");

        if (creditRepository.findByUserId(id) != null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь уже оставил запрос");

        return ResponseEntity.status(HttpStatus.OK).body(creditService.setOffer(creditOffer,id));


    }

    @GetMapping(value = "/{id}/approved_cards")
    public ResponseEntity<?> approvedCards(@PathVariable(name = "id") Long id) {

        if (userRepository.findById(id).isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Нет пользователя с данным аутентификатором");

        CreditOffer creditOffer = creditRepository.findByUserId(id);
        if (!creditOffer.getReady())
            return ResponseEntity.status(HttpStatus.OK).body("Запрос в процессе обработки");


        return ResponseEntity.status(HttpStatus.OK).body(creditOffer.getCards().stream().map(creditCardMapper::toDTO).collect(Collectors.toList()));


    }
}
