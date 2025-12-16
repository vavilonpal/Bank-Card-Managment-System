package com.example.bankcards.util.mapper.bankcard;

import com.example.bankcards.dto.bankcard.request.BankCardAdditionRequest;
import com.example.bankcards.dto.bankcard.response.BankCardResponse;
import com.example.bankcards.dto.bankcard.response.BlockCardResponse;
import com.example.bankcards.dto.bankcard.response.OverallBankCardResponse;
import com.example.bankcards.entity.BankCard;
import com.example.bankcards.entity.CardBlock;
import com.example.bankcards.entity.User;
import com.example.bankcards.util.enums.bankcard.BankCardStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T20:41:56+0200",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class BankCardMapperImpl implements BankCardMapper {

    @Autowired
    private CvvMapper cvvMapper;

    @Override
    public BankCard toEntity(BankCardAdditionRequest additionRequest) {
        if ( additionRequest == null ) {
            return null;
        }

        BankCard.BankCardBuilder bankCard = BankCard.builder();

        bankCard.cvvHash( cvvMapper.encode( additionRequest.getCvv() ) );
        bankCard.cardBalance( additionRequest.getCardBalance() );
        bankCard.cardNumber( additionRequest.getCardNumber() );
        bankCard.cardHolder( additionRequest.getCardHolder() );
        bankCard.expirationMonth( additionRequest.getExpirationMonth() );
        bankCard.expirationYear( additionRequest.getExpirationYear() );
        bankCard.cardStatus( additionRequest.getCardStatus() );

        return bankCard.build();
    }

    @Override
    public BankCardResponse toResponse(BankCard card) {
        if ( card == null ) {
            return null;
        }

        BankCardResponse.BankCardResponseBuilder bankCardResponse = BankCardResponse.builder();

        bankCardResponse.id( card.getId() );
        bankCardResponse.cardHolder( card.getCardHolder() );
        bankCardResponse.cardBalance( card.getCardBalance() );
        bankCardResponse.expirationMonth( card.getExpirationMonth() );
        bankCardResponse.expirationYear( card.getExpirationYear() );
        bankCardResponse.cardStatus( card.getCardStatus() );
        bankCardResponse.createdAt( card.getCreatedAt() );

        bankCardResponse.cardNumber( BankCardResponse.maskCardNumber(card.getCardNumber()) );

        return bankCardResponse.build();
    }

    @Override
    public OverallBankCardResponse toOverallResponse(BankCard card) {
        if ( card == null ) {
            return null;
        }

        OverallBankCardResponse.OverallBankCardResponseBuilder overallBankCardResponse = OverallBankCardResponse.builder();

        overallBankCardResponse.userId( cardUserId( card ) );
        overallBankCardResponse.id( card.getId() );
        overallBankCardResponse.cardBalance( card.getCardBalance() );
        overallBankCardResponse.cardNumber( card.getCardNumber() );
        overallBankCardResponse.cardHolder( card.getCardHolder() );
        overallBankCardResponse.cvvHash( card.getCvvHash() );
        overallBankCardResponse.expirationMonth( card.getExpirationMonth() );
        overallBankCardResponse.expirationYear( card.getExpirationYear() );
        overallBankCardResponse.cardStatus( card.getCardStatus() );
        overallBankCardResponse.createdAt( card.getCreatedAt() );
        overallBankCardResponse.blockCardRequests( cardBlockListToBlockCardResponseList( card.getBlockCardRequests() ) );

        return overallBankCardResponse.build();
    }

    private UUID cardUserId(BankCard bankCard) {
        User user = bankCard.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }

    protected BlockCardResponse cardBlockToBlockCardResponse(CardBlock cardBlock) {
        if ( cardBlock == null ) {
            return null;
        }

        BlockCardResponse blockCardResponse = new BlockCardResponse();

        return blockCardResponse;
    }

    protected List<BlockCardResponse> cardBlockListToBlockCardResponseList(List<CardBlock> list) {
        if ( list == null ) {
            return null;
        }

        List<BlockCardResponse> list1 = new ArrayList<BlockCardResponse>( list.size() );
        for ( CardBlock cardBlock : list ) {
            list1.add( cardBlockToBlockCardResponse( cardBlock ) );
        }

        return list1;
    }
}
