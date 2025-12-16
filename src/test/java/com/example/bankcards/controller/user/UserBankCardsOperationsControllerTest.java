package com.example.bankcards.controller.user;

import com.example.bankcards.dto.bankcard.request.BlockBankCardRequest;
import com.example.bankcards.dto.bankcard.request.TransferRequest;
import com.example.bankcards.dto.bankcard.response.BalanceResponse;
import com.example.bankcards.dto.bankcard.response.BankCardResponse;
import com.example.bankcards.dto.bankcard.response.BlockCardResponse;
import com.example.bankcards.dto.bankcard.response.TransferResponse;
import com.example.bankcards.entity.BankCard;
import com.example.bankcards.entity.CardBlock;
import com.example.bankcards.service.CardBlockService;
import com.example.bankcards.service.UserBankCardOperationsService;
import com.example.bankcards.util.enums.bankcard.BankCardBlockReason;
import com.example.bankcards.util.mapper.bankcard.BankCardBlockMapper;
import com.example.bankcards.util.mapper.bankcard.BankCardMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserBankCardsOperationsController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserBankCardsOperationsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardBlockService cardBlockService;

    @MockBean
    private UserBankCardOperationsService bankCardOperationsService;

    @MockBean
    private BankCardMapper bankCardMapper;

    @MockBean
    private BankCardBlockMapper bankCardBlockMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnOwnCards() throws Exception {
        BankCard card = new BankCard(); // пример сущности
        BankCardResponse responseDto = new BankCardResponse();
        responseDto.setId(UUID.randomUUID());
        responseDto.setCardHolder("Ivan Ivanov");

        Page<BankCard> pageCards = new PageImpl<>(List.of(card));
        when(bankCardOperationsService.getOwnCards(anyString(), anyInt(), anyInt()))
                .thenReturn(pageCards);
        when(bankCardMapper.toResponse(any(BankCard.class))).thenReturn(responseDto);

        mockMvc.perform(get("/api/v1/user/bankcards")
                        .param("page", "0")
                        .param("size", "5")
                        .param("searchQuery", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].cardHolder").value("Ivan Ivanov"));
    }

    @Test
    void shouldCreateBlockRequest() throws Exception {
        BlockBankCardRequest request = new BlockBankCardRequest();
        request.setCardId(UUID.randomUUID());
        request.setReason(BankCardBlockReason.LOST);
        request.setTemporary(true);

        CardBlock block = new CardBlock();
        block.setId(1L);
        BlockCardResponse response = new BlockCardResponse();
        response.setCardBlockId(block.getId());

        when(cardBlockService.createBlockRequest(any(BlockBankCardRequest.class))).thenReturn(block);
        when(bankCardBlockMapper.toResponse(any(CardBlock.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/user/bankcards/block")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardBlockId").value(block.getId().toString()));
    }

    @Test
    void shouldTransferBetweenOwnCards() throws Exception {
        TransferRequest request = new TransferRequest();
        request.setFromCardId(UUID.randomUUID());
        request.setToCardId(UUID.randomUUID());
        request.setAmount(new BigDecimal("100.00"));

        TransferResponse response = new TransferResponse();
        response.setTransferAmount(request.getAmount());

        when(bankCardOperationsService.transferBetweenOwnCards(any(), any(), any())).thenReturn(response);

        mockMvc.perform(patch("/api/v1/user/bankcards/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transferAmount").value(100.00));
    }


    @Test
    void shouldReturnCardBalance() throws Exception {
        UUID cardId = UUID.randomUUID();
        String cardNumber = "1234123412341234";
        BalanceResponse response = new BalanceResponse();
        response.setCardNumber(cardNumber);
        response.setBalance(new BigDecimal("500.00"));

        when(bankCardOperationsService.getOwnCardBalance(cardId)).thenReturn(response);

        mockMvc.perform(get("/api/v1/user/bankcards/{cardId}/balance", cardId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(500.00))
                .andExpect(jsonPath("$.cardNumber").value(BankCardResponse.maskCardNumber(cardNumber)));
    }


}

