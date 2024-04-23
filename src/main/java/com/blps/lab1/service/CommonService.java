package com.blps.lab1.service;

import com.blps.lab1.repo.CreditRepository;
import com.blps.lab1.repo.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CommonService {
    private final UserRepository userRepository;
    private final CreditRepository creditRepository;

    public CommonService(UserRepository userRepository, CreditRepository creditRepository) {
        this.userRepository = userRepository;
        this.creditRepository = creditRepository;
    }

    public ResponseEntity<?> userCheck(Long id) {
        if (userRepository.findById(id).isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Нет пользователя с данным аутентификатором");
        return null;
    }
    public ResponseEntity<?> offerExistenceCheck(Long id, boolean shouldExists) {
        if (shouldExists && creditRepository.findByUserId(id) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь еще не оставил запрос");
        } else if (!shouldExists && creditRepository.findByUserId(id) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь уже оставил запрос");
        }
        return null;
    }


}