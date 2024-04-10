package com.blps.lab1.repo;

import com.blps.lab1.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
