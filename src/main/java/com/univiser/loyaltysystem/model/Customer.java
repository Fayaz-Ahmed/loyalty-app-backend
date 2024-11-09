package com.univiser.loyaltysystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @Column(nullable = false)
    private int points = 0;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
