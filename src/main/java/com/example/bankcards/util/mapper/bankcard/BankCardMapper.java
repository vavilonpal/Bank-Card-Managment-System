package com.example.bankcards.util.mapper.bankcard;


import com.example.bankcards.dto.bankcard.request.BankCardAdditionRequest;
import com.example.bankcards.entity.BankCard;
import com.example.bankcards.util.enums.bankcard.BankCardStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses ={
                UserMapper.class,
                CvvMapper.class
        },
        imports = BankCardStatus.class
)
interface BankCardMapper {
    @Mapping(target = "id", ignore = true)

    @Mapping(
            target = "user",
            source = "userId",
            qualifiedByName = "userFromId"
    )
    @Mapping(
            target = "cvvHash",
            source = "cvv",
            qualifiedByName = "encodeCvv"
    )
    @Mapping(target = "createdAt", ignore = true)
    BankCard toEntity(BankCardAdditionRequest additionRequest);
}
