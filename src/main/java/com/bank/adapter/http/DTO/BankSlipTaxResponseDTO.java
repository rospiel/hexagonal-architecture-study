package com.bank.adapter.http.DTO;

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
public class BankSlipTaxResponseDTO {

    @JsonProperty("code")
    private String code;

    @JsonProperty("type")
    private BankSlipTypeEnum type;

    @JsonProperty("value")
    private BigDecimal value;

    @JsonProperty("dueDate")
    private LocalDate dueDate;

    @JsonProperty("violation")
    private String violation;
}
