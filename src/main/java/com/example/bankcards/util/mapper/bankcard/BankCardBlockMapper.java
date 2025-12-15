package com.example.bankcards.util.mapper.bankcard;


import com.example.bankcards.dto.bankcard.request.BlockBankCardRequest;
import com.example.bankcards.entity.CardBlock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BankCardBlockMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "card", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "blockedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    CardBlock toEntity(BlockBankCardRequest request);
}
