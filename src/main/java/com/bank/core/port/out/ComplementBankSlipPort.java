package com.bank.core.port.out;

import com.bank.core.domain.BankSlip;

public interface ComplementBankSlipPort {

    BankSlip execute(String code);
}
