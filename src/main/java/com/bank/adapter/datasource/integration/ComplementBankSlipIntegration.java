package com.bank.adapter.datasource.integration;

import com.bank.adapter.datasource.integration.DTO.BankSlipDTO;
import com.bank.adapter.datasource.integration.client.ComplementBankSlipClient;
import com.bank.adapter.mapper.BankSlipMapper;
import com.bank.core.domain.BankSlip;

import com.bank.core.port.out.ComplementBankSlipPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class ComplementBankSlipIntegration implements ComplementBankSlipPort {

    private final ComplementBankSlipClient complementBankSlipClient;
    private final BankSlipMapper bankSlipMapper;

    @Override
    public BankSlip execute(String code) {
        BankSlipDTO bankSlipDTO = null;

        try {
            bankSlipDTO = complementBankSlipClient.execute(code);
        } catch (Exception error) {
            log.error("It was not possible to find bank slip with code.: ".concat(code));
            log.error(error.getMessage());
            throw new RuntimeException(error);
        }

        return bankSlipMapper.toDomain(bankSlipDTO);
    }
}
