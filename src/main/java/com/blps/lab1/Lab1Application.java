package com.blps.lab1;

import com.blps.lab1.dto.CreditCardDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Lab1Application extends CreditCardDTO {

    public static void main(String[] args) {
        SpringApplication.run(Lab1Application.class, args);
    }
}
