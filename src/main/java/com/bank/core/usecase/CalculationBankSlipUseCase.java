package com.bank.core.usecase;

import com.bank.adapter.mapper.BankSlipMapper;
import com.bank.core.domain.BankSlip;
import com.bank.core.domain.BankSlipTax;
import com.bank.core.domain.enums.BankSlipTypeEnum;
import com.bank.core.domain.enums.ExceptionTypeCalculationBankSlip;
import com.bank.core.port.in.CalculationBankSlipPort;
import com.bank.core.port.out.ComplementBankSlipPort;
import com.bank.core.port.out.PersistCalculationBankSlipPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Service
@RequiredArgsConstructor
public class CalculationBankSlipUseCase implements CalculationBankSlipPort {

    private static final BigDecimal TAX_DAY = BigDecimal.valueOf(0.015);
    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
    private final ComplementBankSlipPort complementBankSlipPort;
    private final PersistCalculationBankSlipPort persistCalculationBankSlipPort;
    private final BankSlipMapper bankSlipMapper;

    @Override
    public BankSlipTax execute(String code, LocalDate paymentDate) {
        BankSlip bankSlip = complementBankSlipPort.execute(code);
        BankSlipTax bankSlipTaxValidated = validate(bankSlip);

        if (isFalse(bankSlipTaxValidated.getViolation().isPresent())) {
            Long daysOverdue = getDaysOverdue(bankSlip, paymentDate);
            BigDecimal taxes = calculateTaxes(bankSlip, daysOverdue);
            BankSlipTax bankSlipTax = bankSlipMapper.toBankSlipTaxEntityBy(bankSlip, paymentDate, taxes);
            persistCalculationBankSlipPort.execute(bankSlipTax);
            return bankSlipTax;
        }

        return bankSlipTaxValidated;
    }

    private Long getDaysOverdue(BankSlip bankSlip, LocalDate paymentDate) {
        return ChronoUnit.DAYS.between(bankSlip.getDueDate(), paymentDate);
    }

    private BigDecimal calculateTaxDay(BankSlip bankSlip) {
        return TAX_DAY.multiply(bankSlip.getValue()).divide(ONE_HUNDRED);
    }

    private BigDecimal calculateTaxes(BankSlip bankSlip, Long daysOverdue) {
        BigDecimal taxDay = calculateTaxDay(bankSlip);
        return taxDay.multiply(BigDecimal.valueOf(daysOverdue)).setScale(2, RoundingMode.HALF_EVEN);
    }

    private BankSlipTax validate(BankSlip bankSlip) {
        BankSlipTax bankSlipTax = new BankSlipTax();
        bankSlipTax.setViolation(Optional.empty());
        setBankSlipNullable(bankSlip, bankSlipTax);
        setInvalidTypeBankSlip(bankSlip, bankSlipTax);
        setDueDateBankSlipOnTime(bankSlip, bankSlipTax);
        return bankSlipTax;
    }

    private void setBankSlipNullable(BankSlip bankSlip, BankSlipTax bankSlipTax) {
        bankSlipTax.getViolation().ifPresentOrElse(
                (value) -> {},
                () -> {
                    if (isNull(bankSlip)) {
                        bankSlipTax.setViolation(Optional.of(ExceptionTypeCalculationBankSlip.INVALID_BANK_SLIP));
                    }
                });
    }

    private void setInvalidTypeBankSlip(BankSlip bankSlip, BankSlipTax bankSlipTax) {
        bankSlipTax.getViolation().ifPresentOrElse(
                (value) -> {},
                () -> {
                    if (bankSlip.getType().equals(BankSlipTypeEnum.OTHER) == false) {
                        bankSlipTax.setViolation(Optional.of(ExceptionTypeCalculationBankSlip.INVALID_TYPE_BANK_SLIP));
                    }
                });
    }

    private void setDueDateBankSlipOnTime(BankSlip bankSlip, BankSlipTax bankSlipTax) {
        bankSlipTax.getViolation().ifPresentOrElse(
                (value) -> {},
                () -> {
                    if (bankSlip.getDueDate().isAfter(LocalDate.now())) {
                        bankSlipTax.setViolation(Optional.of(ExceptionTypeCalculationBankSlip.DUE_DATE_ON_TIME));
                    }
                });
    }
}
