package com.example.bankcards.dto.bankcard.request;

import com.example.bankcards.util.enums.bankcard.BankCardBlockReason;
import com.example.bankcards.util.enums.bankcard.BankCardBlockStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BlockBankCardRequest {
    @NotNull(message = "Card ID is required")
    private UUID cardId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID requestedBy;

    @NotBlank(message = "Reason is required")
    private BankCardBlockReason reason;

    @Size(max = 200, message = "Notes  length limit is 200")
    private String notes;

    private Boolean temporary = false;

    private Boolean notifyClient = true;
}
