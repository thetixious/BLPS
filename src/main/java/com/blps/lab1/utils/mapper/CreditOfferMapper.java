package com.blps.lab1.utils.mapper;

import com.blps.lab1.dto.CreditOfferDTO;
import com.blps.lab1.model.CreditOffer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CreditOfferMapper {
    @Mapping(source = "card_user.email", target = "email")
    @Mapping(source = "card_user.passport", target = "passport")
    @Mapping(source = "card_user.salary", target = "salary")
    @Mapping(source = "credit_limit", target = "creditLimit")
    @Mapping(source = "approved", target = "approved")
    @Mapping(source = "ready", target = "ready")
    @Mapping(source = "goal", target = "goal")
    @Mapping(source = "bonus", target = "bonus")
    CreditOfferDTO toDTO(CreditOffer creditOffer);

    @Mapping(source = "goal", target = "goal")
    @Mapping(source = "bonus", target = "bonus")
    @Mapping(source = "creditLimit", target = "credit_limit")

    CreditOffer toEntity(CreditOfferDTO creditOfferDTO);

}
