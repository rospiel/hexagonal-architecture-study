package com.bank.adapter.http;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.bank.adapter.http.DTO.BankSlipTaxRequestDTO;
import com.bank.adapter.http.DTO.BankSlipTaxResponseDTO;
import com.bank.adapter.mapper.BankSlipMapper;
import com.bank.core.domain.BankSlipTax;
import com.bank.core.port.in.CalculationBankSlipPort;
import com.bank.utils.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@RestController
@RequestMapping("bank-slip")
@RequiredArgsConstructor
@Tag(name = "BankSlip")
@Validated
public class BankSlipController {

    private final CalculationBankSlipPort calculationBankSlipPort;
    private final BankSlipMapper bankSlipMapper;


    @Operation(summary = "Save tax of bank slip", description = "Calculates tax of a bank slip overdue", responses = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "422", description = "It was not possible to process the request", content = @Content(schema = @Schema))
    })
    @PostMapping(value = Constants.VERSION_1, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BankSlipTaxResponseDTO> save(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Properties of a bank slip to calculate tax", required = true) @Valid @RequestBody BankSlipTaxRequestDTO request) {
        BankSlipTax bankSlipTax = calculationBankSlipPort.execute(request.getCode(), request.getPaymentDate());
        BankSlipTaxResponseDTO bankSlipTaxResponse = bankSlipMapper.toBankSlipTaxResponseDTOBy(bankSlipTax);
        if (isEmpty(bankSlipTaxResponse.getViolation())) {
            return ResponseEntity.ok()
                    .cacheControl(CacheControl.maxAge(1, TimeUnit.MINUTES))
                    .body(bankSlipTaxResponse);
        }


        return ResponseEntity.unprocessableEntity()
                .body(bankSlipTaxResponse);

    }

}
