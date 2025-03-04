package com.bank.core.domain;

import com.bank.core.domain.enums.BankSlipTypeEnum;
import com.bank.core.domain.enums.ExceptionTypeCalculationBankSlip;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankSlipTax {

    private String code;
    private BankSlipTypeEnum type;
    private BigDecimal originalValue;
    private BigDecimal updatedValue;
    private BigDecimal taxes;
    private LocalDate dueDate;
    private LocalDate paymentDate;
    private Optional<ExceptionTypeCalculationBankSlip> violation;
}
