package com.bank.adapter.datasource.integration.DTO;

import com.bank.core.domain.enums.BankSlipTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankSlipDTO {

    @JsonProperty("code")
    private String code;

    @JsonProperty("dueDate")
    private LocalDate dueDate;

    @JsonProperty("value")
    private BigDecimal value;

    @JsonProperty("type")
    private BankSlipTypeEnum type;
}
