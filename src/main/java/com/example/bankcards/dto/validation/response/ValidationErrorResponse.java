package com.example.bankcards.dto.validation.response;


import com.example.bankcards.util.validation.Violation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class ValidationErrorResponse {
    private final List<Violation> violations;
}