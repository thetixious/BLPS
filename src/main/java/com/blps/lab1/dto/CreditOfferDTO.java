package com.blps.lab1.dto;

import com.blps.lab1.utils.Bonus;
import com.blps.lab1.utils.Goal;
import lombok.Data;

@Data
public class CreditOfferDTO {

    private String email;
    private String passport;
    private Double salary;
    private Double creditLimit;
    private Boolean approved;
    private Boolean ready;
    private Goal goal;
    private Bonus bonus;

}
