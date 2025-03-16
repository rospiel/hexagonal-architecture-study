package com.bank.utils;

import com.bank.adapter.http.DTO.BankSlipTaxRequestDTO;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;

@UtilityClass
public class BankSlipTaxRequestDTOMother {

    public static BankSlipTaxRequestDTO buildBankSlipTaxRequestDTOComplete() {
        return BankSlipTaxRequestDTO.builder()
                .code(RandomStringUtils.randomNumeric(20))
                .paymentDate(LocalDate.now())
                .build();

    }
}
