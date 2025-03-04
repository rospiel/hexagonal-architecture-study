package com.bank.adapter.mapper;

import com.bank.adapter.datasource.integration.DTO.BankSlipDTO;
import com.bank.adapter.datasource.integration.database.entity.BankSlipTaxEntity;
import com.bank.adapter.http.DTO.BankSlipTaxResponseDTO;
import com.bank.core.domain.BankSlip;
import com.bank.core.domain.BankSlipTax;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface BankSlipMapper {

    default BankSlipTax toBankSlipTaxEntityBy(BankSlip bankSlip, LocalDate paymentDate, BigDecimal taxes) {
        return BankSlipTax.builder()
                .taxes(taxes)
                .paymentDate(paymentDate)
                .updatedValue(bankSlip.getValue().add(taxes))
                .code(bankSlip.getCode())
                .type(bankSlip.getType())
                .originalValue(bankSlip.getValue())
                .dueDate(bankSlip.getDueDate())
                .violation(Optional.empty())
                .build();
    }

    default BankSlipTaxResponseDTO toBankSlipTaxResponseDTOBy(BankSlipTax bankSlipTax) {
        BankSlipTaxResponseDTO.BankSlipTaxResponseDTOBuilder bankSlipTaxResponseDTOBuilder = BankSlipTaxResponseDTO.builder()
                .code(bankSlipTax.getCode())
                .type(bankSlipTax.getType())
                .value(bankSlipTax.getUpdatedValue())
                .dueDate(bankSlipTax.getDueDate());

        if (bankSlipTax.getViolation().isPresent()) {
            bankSlipTaxResponseDTOBuilder.violation(bankSlipTax.getViolation().get().getViolationMessage());
        }

        return bankSlipTaxResponseDTOBuilder.build();
    }

    BankSlip toDomain(BankSlipDTO bankSlip);
    BankSlipTaxEntity toEntity(BankSlipTax bankSlipTax);
}
