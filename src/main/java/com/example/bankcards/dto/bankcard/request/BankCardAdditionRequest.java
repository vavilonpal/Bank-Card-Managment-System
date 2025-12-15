package com.example.bankcards.dto.bankcard.request;


import com.example.bankcards.entity.User;
import com.example.bankcards.util.enums.bankcard.BankCardStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankCardAdditionRequest {

    @NotNull(message = "User id is required")
    private UUID userId;

    @NotBlank(message = "Card number is required")
    @Pattern(
            regexp = "^[0-9]{16,19}$",
            message = "Card number must contain 16 to 19 digits"
    )
    private String cardNumber;

    @NotNull(message = "Card balance is required")
    @DecimalMin(
            value = "0.00",
            inclusive = true,
            message = "Card balance cannot be negative"
    )
    @Digits(integer = 17, fraction = 2, message = "Invalid balance format")
    private BigDecimal cardBalance;

    @NotBlank(message = "Card holder is required")
    @Size(min = 2, max = 150, message = "Card holder must be between 2 and 150 characters")
    private String cardHolder;

    @NotBlank(message = "CVV is required")
    @Pattern(
            regexp = "^[0-9]{3}$",
            message = "CVV must contain exactly 3 digits"
    )
    private String cvv;

    @NotNull(message = "Expiration month is required")
    @Min(value = 1, message = "Expiration month must be between 1 and 12")
    @Max(value = 12, message = "Expiration month must be between 1 and 12")
    private Integer expirationMonth;


    @NotNull(message = "Expiration year is required")
    @Min(value = 2024, message = "Expiration year must be current or future year")
    private Integer expirationYear;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BankCardStatus cardStatus = BankCardStatus.INACTIVE;

}
