package com.example.bankcards.util.mapper.bankcard;

import com.example.bankcards.dto.bankcard.request.BankCardAdditionRequest;
import com.example.bankcards.dto.bankcard.response.BankCardResponse;
import com.example.bankcards.entity.BankCard;
import com.example.bankcards.util.enums.bankcard.BankCardStatus;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-15T20:47:16+0200",
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
}
