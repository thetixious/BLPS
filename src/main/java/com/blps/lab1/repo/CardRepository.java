package com.blps.lab1.repo;

import com.blps.lab1.model.Cards;
import com.blps.lab1.utils.Bonus;
import com.blps.lab1.utils.CardType;
import com.blps.lab1.utils.Goal;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CardRepository extends CrudRepository<Cards,Long> {

    List<Cards> findAllByGoalOrBonusAndType(Goal goal, Bonus bonus, CardType cardType);
}
