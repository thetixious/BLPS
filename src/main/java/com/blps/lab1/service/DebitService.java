package com.blps.lab1.service;

import com.blps.lab1.dto.DebitOfferDTO;
import com.blps.lab1.model.Cards;
import com.blps.lab1.model.DebitOffer;
import com.blps.lab1.repo.CardRepository;

import com.blps.lab1.utils.Bonus;
import com.blps.lab1.utils.CardType;
import com.blps.lab1.utils.Goal;
import com.blps.lab1.utils.mapper.DebitOfferMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DebitService {
    private final CardRepository cardRepository;
    private final DebitOfferMapper debitOfferMapper;


    public DebitService(CardRepository cardRepository, DebitOfferMapper debitOfferMapper) {
        this.cardRepository = cardRepository;
        this.debitOfferMapper = debitOfferMapper;

    }


    public List<Cards> getCards(DebitOffer debitOffer) {
        Goal goal = debitOffer.getGoal();
        Bonus bonus = debitOffer.getBonus();
        return cardRepository.findAllByGoalOrBonusAndType(goal, bonus, CardType.DEBIT);
    }

    public DebitOfferDTO debitOfferToDTO(DebitOffer debitOffer) {
        return debitOfferMapper.toDTO(debitOffer);
    }

    public DebitOffer DTOToDebitOffer(DebitOfferDTO debitOfferDTO) {
        return debitOfferMapper.toEntity(debitOfferDTO);
    }


}
