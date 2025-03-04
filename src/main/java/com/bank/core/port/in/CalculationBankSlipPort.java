package com.bank.core.port.in;

import com.bank.core.domain.BankSlipTax;

import java.time.LocalDate;

public interface CalculationBankSlipPort {

    BankSlipTax execute(String code, LocalDate paymentDate);
}
