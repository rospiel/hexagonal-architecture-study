package com.bank.adapter.http;

import com.bank.adapter.http.DTO.BankSlipTaxRequestDTO;
import com.bank.adapter.http.DTO.BankSlipTaxResponseDTO;
import com.bank.adapter.mapper.BankSlipMapper;
import com.bank.core.domain.BankSlipTax;
import com.bank.core.port.in.CalculationBankSlipPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static com.bank.utils.BankSlipTaxRequestDTOMother.buildBankSlipTaxRequestDTOComplete;
import static com.bank.utils.BankSlipTaxMother.buildBankSlipTaxComplete;
import static com.bank.utils.BankSlipTaxResponseDTOMother.buildBankSlipTaxResponseDTOComplete;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = BankSlipController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BankSlipControllerTest {

    @MockitoBean
    private CalculationBankSlipPort calculationBankSlipPort;

    @MockitoBean
    private BankSlipMapper bankSlipMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("save should persist a tax of bank slip and return the values")
    @Order(1)
    void save_PersistTaxOfBankSlip_WhenSuccessful() throws Exception {
        BankSlipTax bankSlipTax = buildBankSlipTaxComplete();
        BDDMockito.when(calculationBankSlipPort.execute(anyString(), any(LocalDate.class))).thenReturn(bankSlipTax);
        BankSlipTaxResponseDTO bankSlipTaxResponse = buildBankSlipTaxResponseDTOComplete();
        BDDMockito.when(bankSlipMapper.toBankSlipTaxResponseDTOBy(bankSlipTax)).thenReturn(bankSlipTaxResponse);

        BankSlipTaxRequestDTO bankSlipTaxRequest = buildBankSlipTaxRequestDTOComplete();

        mockMvc.perform(post("/bank-slip/v1")
                .content(objectMapper.writeValueAsString(bankSlipTaxRequest))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Cache-Control", "max-age=60"))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(bankSlipTaxResponse)));

        BDDMockito.verify(calculationBankSlipPort).execute(anyString(), any(LocalDate.class));
        BDDMockito.verify(bankSlipMapper).toBankSlipTaxResponseDTOBy(any(BankSlipTax.class));
    }

    @Test
    @DisplayName("save should return HTTP code 400 when code nullable on body requisition")
    @Order(2)
    void save_PersistTaxOfBankSlip_WhenNullableCode() throws Exception {
        BankSlipTaxRequestDTO bankSlipTaxRequest = buildBankSlipTaxRequestDTOComplete();
        bankSlipTaxRequest.setCode(null);

        MvcResult mvcResult = mockMvc.perform(post("/bank-slip/v1")
                        .content(objectMapper.writeValueAsString(bankSlipTaxRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

        Exception resolvedException = mvcResult.getResolvedException();
        Assertions.assertThat(resolvedException.getMessage()).contains("code must be informed and can't be empty");


        BDDMockito.verifyNoInteractions(calculationBankSlipPort);
        BDDMockito.verifyNoInteractions(bankSlipMapper);
    }

}