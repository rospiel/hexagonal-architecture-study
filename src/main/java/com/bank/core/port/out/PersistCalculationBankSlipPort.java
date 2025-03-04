package com.bank.core.port.out;

import com.bank.core.domain.BankSlipTax;

public interface PersistCalculationBankSlipPort {

    void execute(BankSlipTax bankSlipTax);
}
