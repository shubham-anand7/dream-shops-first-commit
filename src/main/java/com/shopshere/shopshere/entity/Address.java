package com.shopshere.shopshere.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private String city;
    private String state;
    private String zipCode;

    // 🔥 THIS IS MISSING IN YOUR PROJECT
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}