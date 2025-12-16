package com.example.bankcards.controller.admin.bankcards;

import com.example.bankcards.dto.bankcard.request.BankCardAdditionRequest;
import com.example.bankcards.dto.bankcard.request.BlockBankCardRequest;
import com.example.bankcards.dto.bankcard.response.BankCardResponse;
import com.example.bankcards.dto.bankcard.response.OverallBankCardResponse;
import com.example.bankcards.entity.BankCard;
import com.example.bankcards.entity.CardBlock;
import com.example.bankcards.service.BankCardsService;
import com.example.bankcards.service.CardBlockService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = AdminManageBankCardsController.class)
@AutoConfigureMockMvc(addFilters = false)  // without security
class AdminManageBankCardsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankCardsService bankCardsService;

    @MockBean
    private CardBlockService cardBlockService;

    @MockBean
    private BankCardMapper bankCardMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateBankCard() throws Exception {
        BankCardAdditionRequest request = new BankCardAdditionRequest();
        request.setUserId(UUID.randomUUID());
        request.setCardNumber("1234567812345678");
        request.setCardBalance(new BigDecimal("1000.00"));
        request.setCardHolder("IVAN IVANOV");
        request.setCvv("123");
        request.setExpirationMonth(12);
        request.setExpirationYear(2026);

        BankCard card = new BankCard();
        BankCardResponse response = new BankCardResponse();

        when(bankCardsService.createCard(any())).thenReturn(card);
        when(bankCardMapper.toResponse(card)).thenReturn(response);

        mockMvc.perform(post("/api/v1/admin/bankcards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(bankCardsService).createCard(any());
        verify(bankCardMapper).toResponse(card);
    }

    @Test
    void shouldActivateCard() throws Exception {
        UUID cardId = UUID.randomUUID();

        BankCard card = new BankCard();
        BankCardResponse response = new BankCardResponse();

        when(bankCardsService.activateCardById(cardId)).thenReturn(card);
        when(bankCardMapper.toResponse(card)).thenReturn(response);

        mockMvc.perform(patch("/api/v1/admin/bankcards/{cardId}/activate", cardId))
                .andExpect(status().isOk());

        verify(bankCardsService).activateCardById(cardId);
    }

    @Test
    void shouldBlockCard() throws Exception {
        BlockBankCardRequest request = new BlockBankCardRequest();
        request.setCardId(UUID.randomUUID());

        CardBlock cardBlock = new CardBlock();
        cardBlock.setId(2L);
        cardBlock.setTemporary(false);

        when(cardBlockService.createBlockRequest(any()))
                .thenReturn(cardBlock);

        mockMvc.perform(patch("/api/v1/admin/bankcards/block")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Successful card block"));

        verify(cardBlockService).createBlockRequest(any());
        verify(cardBlockService)
                .confirmBlockRequest(cardBlock.getId(), cardBlock.getTemporary());
    }

    @Test
    void shouldDeleteCard() throws Exception {
        UUID cardId = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/admin/bankcards/{cardId}", cardId))
                .andExpect(status().isOk())
                .andExpect(content().string("Successful card delete!"));

        verify(bankCardsService).deleteCard(cardId);
    }

    @Test
    void shouldGetAllCardsWithPaging() throws Exception {
        BankCard card = new BankCard();
        OverallBankCardResponse response = new OverallBankCardResponse();

        Page<BankCard> page = new PageImpl<>(List.of(card));

        when(bankCardsService.getAllCards(null, 0, 10))
                .thenReturn(page);
        when(bankCardMapper.toOverallResponse(card))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/admin/bankcards")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(1));
    }





}


