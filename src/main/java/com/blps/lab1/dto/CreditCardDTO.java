package com.blps.lab1.dto;

import com.blps.lab1.utils.Bonus;
import com.blps.lab1.utils.CardType;
import com.blps.lab1.utils.Goal;
import lombok.Data;

@Data
public class CreditCardDTO {

    private String name;
    private CardType cardType;
    private Double creditLimit;
    private Goal goal;
    private Bonus bonus;

}
