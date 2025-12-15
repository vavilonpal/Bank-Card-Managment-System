package com.example.bankcards.util.mapper.bankcard;


import com.example.bankcards.dto.bankcard.request.BankCardAdditionRequest;
import com.example.bankcards.dto.bankcard.response.BankCardResponse;
import com.example.bankcards.entity.BankCard;
import com.example.bankcards.util.enums.bankcard.BankCardStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses ={
                CvvMapper.class
        },
        imports = {BankCardStatus.class}
)
public interface BankCardMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(
            target = "cvvHash",
            source = "cvv",
            qualifiedByName = "encodeCvv"
    )
    @Mapping(target = "createdAt", ignore = true)
    BankCard toEntity(BankCardAdditionRequest additionRequest);

    @Mapping(target = "cardNumber", expression = "java(BankCardResponse.maskCardNumber(card.getCardNumber()))")
    BankCardResponse toResponse(BankCard card);
}