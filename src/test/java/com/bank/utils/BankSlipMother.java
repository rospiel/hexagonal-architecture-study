package com.bank.utils;

import com.bank.core.domain.BankSlip;
import com.bank.core.domain.enums.BankSlipTypeEnum;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;


@UtilityClass
public class BankSlipMother {

    public static BankSlip buildBankSlipUsual() {
        return BankSlip.builder()
                .code(RandomStringUtils.randomNumeric(20))
                .type(BankSlipTypeEnum.USUAL)
                .value(new BigDecimal(String.join(".", RandomStringUtils.randomNumeric(3), RandomStringUtils.randomNumeric(2))))
                .dueDate(LocalDate.now())
                .build();
    }
}
