package com.shopshere.shopshere.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String brand;
    @ManyToOne
    private Category category;
    private Double price;
    private Integer stock;


    private String imageUrl;

    @Column(length = 2000)
    private String description;
}