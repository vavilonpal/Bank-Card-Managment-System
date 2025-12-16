package com.example.bankcards.util.mapper.bankcard;

import com.example.bankcards.dto.bankcard.request.BlockBankCardRequest;
import com.example.bankcards.dto.bankcard.response.BlockCardResponse;
import com.example.bankcards.entity.CardBlock;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T20:41:56+0200",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class BankCardBlockMapperImpl implements BankCardBlockMapper {

    @Override
    public CardBlock toEntity(BlockBankCardRequest request) {
        if ( request == null ) {
            return null;
        }

        CardBlock.CardBlockBuilder cardBlock = CardBlock.builder();

        cardBlock.reason( request.getReason() );
        cardBlock.notes( request.getNotes() );
        cardBlock.temporary( request.getTemporary() );
        cardBlock.notifyClient( request.getNotifyClient() );

        return cardBlock.build();
    }

    @Override
    public BlockCardResponse toResponse(CardBlock cardBlock) {
        if ( cardBlock == null ) {
            return null;
        }

        BlockCardResponse blockCardResponse = new BlockCardResponse();

        blockCardResponse.setCardBlockId( cardBlock.getId() );

        return blockCardResponse;
    }
}
