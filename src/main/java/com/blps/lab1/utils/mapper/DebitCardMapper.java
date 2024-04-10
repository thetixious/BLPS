package com.blps.lab1.utils.mapper;

import com.blps.lab1.dto.DebitCardDTO;
import com.blps.lab1.model.Cards;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface DebitCardMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "goal", target = "goal")
    @Mapping(source = "type", target = "cardType")
    @Mapping(source = "bonus", target = "bonus")
    DebitCardDTO toDTO(Cards card);






}
