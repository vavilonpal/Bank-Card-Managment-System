package com.example.bankcards.dto.bankcard.response;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlockCardResponse {
    private Long cardBlockId;
    private String message = "The card blocking request has been successfully submitted";


}
