package com.bank.adapter.datasource.integration.client;

import com.bank.adapter.datasource.integration.DTO.BankSlipDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(value = "ComplementBankSlip", url = "${beeceptor.api.bank-slip.url}")
public interface ComplementBankSlipClient {

    @GetMapping(value = "/code/{code}", produces = APPLICATION_JSON_VALUE)
    BankSlipDTO execute(@PathVariable String code);
}
