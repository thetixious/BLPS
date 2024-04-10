package com.blps.lab1.service;

import com.blps.lab1.dto.CreditOfferDTO;
import com.blps.lab1.model.Cards;
import com.blps.lab1.model.CreditOffer;
import com.blps.lab1.repo.CardRepository;
import com.blps.lab1.repo.CreditRepository;
import com.blps.lab1.repo.UserRepository;
import com.blps.lab1.utils.Bonus;
import com.blps.lab1.utils.CardType;
import com.blps.lab1.utils.Goal;
import com.blps.lab1.utils.mapper.CreditOfferMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CreditOfferMapper creditOfferMapper;
    private final CreditRepository creditRepository;

    public CreditService(CardRepository cardRepository, UserRepository userRepository, CreditOfferMapper creditOfferMapper,
                         CreditRepository creditRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.creditOfferMapper = creditOfferMapper;
        this.creditRepository = creditRepository;
    }

    public List<Cards> getUncheckedCards(CreditOffer creditOffer) {
        Goal goal = creditOffer.getGoal();
        Bonus bonus = creditOffer.getBonus();
        return cardRepository.findAllByGoalOrBonusAndType(goal, bonus, CardType.CREDIT);

    }



    public CreditOfferDTO setOffer(CreditOfferDTO creditOfferDTO, Long id) {
        CreditOffer creditOffer = creditOfferMapper.toEntity(creditOfferDTO);
        creditOffer.setCard_user(userRepository.findById(id).get());
        creditOffer.setReady(false);
        creditOffer.setApproved(false);
        creditOffer.setCards(getUncheckedCards(creditOffer));
        creditOffer.setCredit_limit(creditOfferDTO.getCreditLimit());
        return creditOfferMapper.toDTO(creditRepository.save(creditOffer));

    }


}
