package com.bank.utils;

import com.bank.adapter.http.DTO.BankSlipTaxResponseDTO;
import com.bank.core.domain.enums.BankSlipTypeEnum;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

@UtilityClass
public class BankSlipTaxResponseDTOMother {

    public static BankSlipTaxResponseDTO buildBankSlipTaxResponseDTOComplete() {
        return BankSlipTaxResponseDTO.builder()
                .code(RandomStringUtils.randomNumeric(20))
                .type(BankSlipTypeEnum.OTHER)
                .value((new BigDecimal(String.join(".", RandomStringUtils.randomNumeric(3), RandomStringUtils.randomNumeric(2)))))
                .dueDate(LocalDate.now())
                .build();

    }
}
