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
import com.blps.lab1.utils.mapper.CreditCardMapper;
import com.blps.lab1.utils.mapper.CreditOfferMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreditService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CreditOfferMapper creditOfferMapper;
    private final CreditCardMapper creditCardMapper;
    private final CreditRepository creditRepository;
    private final CommonService commonService;

    public CreditService(CardRepository cardRepository, UserRepository userRepository, CreditOfferMapper creditOfferMapper, CreditCardMapper creditCardMapper,
                         CreditRepository creditRepository, CommonService commonService) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.creditOfferMapper = creditOfferMapper;
        this.creditCardMapper = creditCardMapper;
        this.creditRepository = creditRepository;
        this.commonService = commonService;
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

    public ResponseEntity<?> getApprovedCards(Long id) {

        ResponseEntity<?> userCheckResponse = commonService.userCheck(id);
        ResponseEntity<?> offerCheckResponse = commonService.offerExistenceCheck(id,true);

        if (userCheckResponse != null)
            return userCheckResponse;

        if (offerCheckResponse != null)
            return offerCheckResponse;


        CreditOffer creditOffer = creditRepository.findByUserId(id);
        if (!creditOffer.getReady())
            return ResponseEntity.status(HttpStatus.OK).body("Запрос в процессе обработки");

        return ResponseEntity.status(HttpStatus.OK).body(creditOffer.getCards().stream().map(creditCardMapper::toDTO).collect(Collectors.toList()));

    }
    public ResponseEntity<?> creatOffer(Long id, CreditOfferDTO creditOfferDTO){
        ResponseEntity<?> userCheckResponse = commonService.userCheck(id);
        ResponseEntity<?> offerCheckResponse = commonService.offerExistenceCheck(id,false);

        if (userCheckResponse != null)
            return userCheckResponse;

        if (offerCheckResponse != null)
            return offerCheckResponse;

        return ResponseEntity.status(HttpStatus.OK).body(setOffer(creditOfferDTO, id));
    }

}
