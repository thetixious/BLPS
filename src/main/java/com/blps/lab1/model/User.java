package com.blps.lab1.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name="my_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String email;
    private String passport;
    private Double salary;
    private Boolean is_fill;


    public User(String name, String surname, String email, String passport, Double salary) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.passport = passport;
        this.salary = salary;
    }

    public User() {

    }
}
