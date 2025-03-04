package com.bank.adapter.http.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankSlipTaxRequestDTO {

    @NotBlank(message = "code must be informed and can't be empty")
    @Size(max = 50, message = "code should be at most 50 characters")
    @JsonProperty("code")
    private String code;

    @NotNull(message = "paymentDate must be informed")
    @JsonProperty("paymentDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentDate;
}
