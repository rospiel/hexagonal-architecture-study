package com.bank.core.usecase;

import com.bank.adapter.mapper.BankSlipMapper;
import com.bank.core.domain.BankSlip;
import com.bank.core.domain.BankSlipTax;
import com.bank.core.port.out.ComplementBankSlipPort;
import com.bank.core.port.out.PersistCalculationBankSlipPort;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static com.bank.utils.BankSlipMother.buildBankSlipUsual;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class CalculationBankSlipUseCaseTest {

    private static final String CODE = RandomStringUtils.randomNumeric(20);
    private static final LocalDate PAYMENT_DATE = LocalDate.now();

    @Mock
    private ComplementBankSlipPort complementBankSlipPort;

    @Mock
    private PersistCalculationBankSlipPort persistCalculationBankSlipPort;

    @Mock
    private BankSlipMapper bankSlipMapper;

    @InjectMocks
    private CalculationBankSlipUseCase useCase;

    @Test
    @DisplayName("execute should return violation about invalid type bank")
    void execute_calculationBankSlip_whenInvalidTypeBankSlip() {
        BankSlip bankSlip = buildBankSlipUsual();
        BDDMockito.when(complementBankSlipPort.execute(CODE)).thenReturn(bankSlip);

        BankSlipTax bankSlipTax = useCase.execute(CODE, PAYMENT_DATE);
        assertTrue(bankSlipTax.getViolation().isPresent());
        assertEquals("Invalid type bank slip", bankSlipTax.getViolation().get().getViolationMessage());

        BDDMockito.verify(complementBankSlipPort).execute(anyString());
        BDDMockito.verifyNoInteractions(persistCalculationBankSlipPort);
        BDDMockito.verifyNoInteractions(bankSlipMapper);

    }
}