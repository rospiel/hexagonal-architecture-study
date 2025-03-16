package com.bank.utils;


import com.bank.core.domain.BankSlipTax;
import com.bank.core.domain.enums.BankSlipTypeEnum;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@UtilityClass
public class BankSlipTaxMother {

    public static BankSlipTax buildBankSlipTaxComplete() {
        return BankSlipTax.builder()
                .code(RandomStringUtils.randomNumeric(20))
                .type(BankSlipTypeEnum.OTHER)
                .originalValue((new BigDecimal(String.join(".", RandomStringUtils.randomNumeric(3), RandomStringUtils.randomNumeric(2)))))
                .updatedValue((new BigDecimal(String.join(".", RandomStringUtils.randomNumeric(3), RandomStringUtils.randomNumeric(2)))))
                .taxes((new BigDecimal(String.join(".", RandomStringUtils.randomNumeric(1), RandomStringUtils.randomNumeric(2)))))
                .dueDate(LocalDate.now())
                .paymentDate(LocalDate.now())
                .violation(Optional.empty())
                .build();
    }
}
