package com.univiser.loyaltysystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RedeemPointsDTO {
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Min(value = 1, message = "Points to redeem must be greater than zero")
    private int points;
}
