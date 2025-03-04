package com.bank.core.domain;

import com.bank.core.domain.enums.BankSlipTypeEnum;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankSlip {

    private String code;
    private BankSlipTypeEnum type;
    private BigDecimal value;
    private LocalDate dueDate;
}
