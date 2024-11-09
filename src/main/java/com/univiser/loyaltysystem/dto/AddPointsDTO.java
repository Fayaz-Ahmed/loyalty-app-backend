package com.univiser.loyaltysystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddPointsDTO {
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Min(value = 1, message = "Points must be greater than zero")
    private int points;
}