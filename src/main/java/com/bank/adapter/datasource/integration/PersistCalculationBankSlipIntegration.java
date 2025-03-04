package com.bank.adapter.datasource.integration;

import com.bank.adapter.datasource.integration.database.entity.BankSlipTaxEntity;
import com.bank.adapter.datasource.integration.database.repository.BankSlipTaxRepository;
import com.bank.adapter.mapper.BankSlipMapper;
import com.bank.core.domain.BankSlipTax;
import com.bank.core.port.out.PersistCalculationBankSlipPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PersistCalculationBankSlipIntegration implements PersistCalculationBankSlipPort {

    private final BankSlipTaxRepository bankSlipTaxRepository;
    private final BankSlipMapper bankSlipMapper;

    @Override
    public void execute(BankSlipTax bankSlipTax) {
        BankSlipTaxEntity entity = bankSlipMapper.toEntity(bankSlipTax);
        try {
            bankSlipTaxRepository.save(entity);
        } catch (Exception error) {
            log.error("It was not possible to persist a new BankSlipTax");
            log.error(error.getMessage());
            throw new RuntimeException(error);
        }

    }
}
