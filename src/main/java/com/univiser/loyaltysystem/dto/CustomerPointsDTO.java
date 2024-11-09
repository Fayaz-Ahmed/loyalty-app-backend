package com.univiser.loyaltysystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerPointsDTO {
    private String email;
    private int points;
}