package com.blps.lab1.dto;

import com.blps.lab1.utils.Bonus;
import com.blps.lab1.utils.Goal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class CreditOfferDTO {
    @JsonIgnore
    private String email;
    @JsonIgnore
    private String passport;
    @JsonIgnore
    private Double salary;
    @JsonIgnore
    private Boolean approved;
    @JsonIgnore
    private Boolean ready;

    private Goal goal;
    private Bonus bonus;
    private Double creditLimit;
}
