package com.bank.adapter.http;

import com.bank.adapter.http.DTO.BankSlipTaxRequestDTO;
import com.bank.adapter.http.DTO.BankSlipTaxResponseDTO;
import com.bank.config.IntegrationTestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import static com.bank.utils.BankSlipTaxRequestDTOMother.buildBankSlipTaxRequestDTOComplete;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureWireMock(port = 0)
class BankSlipControllerIT extends IntegrationTestConfig {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("save should persist a tax of bank slip and return the values")
    @Order(1)
    void save_PersistTaxOfBankSlip_WhenSuccessful() throws Exception {
        /* BankSlip bankSlip = buildBankSlipOther();
        stubFor(get(urlEqualTo("/code/10"))
                .willReturn(aResponse().withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE).withBody(objectMapper.writeValueAsString(bankSlip)))); */

        BankSlipTaxRequestDTO bankSlipTaxRequest = buildBankSlipTaxRequestDTOComplete();
        bankSlipTaxRequest.setCode("10");
        HttpEntity<BankSlipTaxRequestDTO> bankSlipTaxRequestDTOHttpEntity = new HttpEntity<>(bankSlipTaxRequest);
        ResponseEntity<BankSlipTaxResponseDTO> response = testRestTemplate
                .exchange("/bank-slip/v1", POST, bankSlipTaxRequestDTOHttpEntity, BankSlipTaxResponseDTO.class);


        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotNull().hasNoNullFieldsOrPropertiesExcept("violation");
    }

    @Test
    @DisplayName("save should return HTTP code 400 when code empty on body requisition")
    @Order(2)
    void save_PersistTaxOfBankSlip_WhenNullableCode() throws Exception {
        BankSlipTaxRequestDTO bankSlipTaxRequest = buildBankSlipTaxRequestDTOComplete();
        bankSlipTaxRequest.setCode(" ");
        HttpEntity<BankSlipTaxRequestDTO> bankSlipTaxRequestDTOHttpEntity = new HttpEntity<>(bankSlipTaxRequest);
        ResponseEntity<String> response = testRestTemplate
                .exchange("/bank-slip/v1", POST, bankSlipTaxRequestDTOHttpEntity, String.class);


        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

}