package com.blps.lab1.repo;

import com.blps.lab1.model.Cards;
import com.blps.lab1.model.CreditOffer;
import com.blps.lab1.model.DebitOffer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CreditRepository extends CrudRepository<CreditOffer,Long> {

    @Query("from CreditOffer co where co.card_user.id = :id")
    CreditOffer findByUserId(@Param("id") Long id);

}
