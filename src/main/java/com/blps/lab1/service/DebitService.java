package com.blps.lab1.service;

import com.blps.lab1.dto.DebitOfferDTO;
import com.blps.lab1.dto.UserDataDTO;
import com.blps.lab1.model.Cards;
import com.blps.lab1.model.DebitOffer;
import com.blps.lab1.model.User;
import com.blps.lab1.repo.CardRepository;

import com.blps.lab1.repo.DebitRepository;
import com.blps.lab1.repo.UserRepository;
import com.blps.lab1.utils.Bonus;
import com.blps.lab1.utils.CardType;
import com.blps.lab1.utils.Goal;
import com.blps.lab1.utils.mapper.DebitCardMapper;
import com.blps.lab1.utils.mapper.DebitOfferMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DebitService {
    private final CardRepository cardRepository;
    private final DebitOfferMapper debitOfferMapper;
    private final DebitCardMapper debitCardMapper;
    private final UserRepository userRepository;
    private final DebitRepository debitRepository;
    private final CommonService commonService;


    public DebitService(CardRepository cardRepository, DebitOfferMapper debitOfferMapper, DebitCardMapper debitCardMapper, UserRepository userRepository, DebitRepository debitRepository, CommonService commonService) {
        this.cardRepository = cardRepository;
        this.debitOfferMapper = debitOfferMapper;
        this.debitCardMapper = debitCardMapper;
        this.userRepository = userRepository;
        this.debitRepository = debitRepository;
        this.commonService = commonService;
    }


    public DebitOfferDTO debitOfferToDTO(DebitOffer debitOffer) {
        return debitOfferMapper.toDTO(debitOffer);
    }

    public DebitOffer DTOToDebitOffer(DebitOfferDTO debitOfferDTO) {
        return debitOfferMapper.toEntity(debitOfferDTO);
    }


    public ResponseEntity<?> getCards(Long id) {

        ResponseEntity<?> userCheckResponse = commonService.userCheck(id);
        ResponseEntity<?> offerCheckResponse = commonService.offerExistenceCheck(id, true);

        if (userCheckResponse != null)
            return userCheckResponse;

        if (offerCheckResponse != null)
            return offerCheckResponse;

        DebitOffer debitOffer = debitRepository.findByUserId(id);
        Goal goal = debitOffer.getGoal();
        Bonus bonus = debitOffer.getBonus();
        List<Cards> cardsList = cardRepository.findAllByGoalOrBonusAndType(goal, bonus, CardType.DEBIT);

        if (cardsList.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Нет карт с таким набором бонусов или целей");

        return ResponseEntity.ok(cardsList.stream().map(debitCardMapper::toDTO).toList());
    }

    public ResponseEntity<?> creatOffer(Long id, DebitOfferDTO debitOfferDTO) {

        ResponseEntity<?> userCheckResponse = commonService.userCheck(id);
        ResponseEntity<?> offerCheckResponse = commonService.offerExistenceCheck(id, false);

        if (userCheckResponse != null)
            return userCheckResponse;

        if (offerCheckResponse != null)
            return offerCheckResponse;


        DebitOffer debitOffer = DTOToDebitOffer(debitOfferDTO);
        debitOffer.setCard_user(userRepository.findById(id).get());

        return ResponseEntity.ok(debitOfferToDTO(debitRepository.save(debitOffer)));
    }



}
