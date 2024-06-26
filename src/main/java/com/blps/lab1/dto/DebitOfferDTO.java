package com.blps.lab1.dto;


import com.blps.lab1.utils.Bonus;
import com.blps.lab1.utils.Goal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class DebitOfferDTO {
    @JsonIgnore
    private Long id;
    private Goal goal;
    private Bonus bonus;



}
