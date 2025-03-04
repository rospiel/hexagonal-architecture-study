package com.bank.adapter.datasource.integration.database.entity;

import com.bank.core.domain.enums.BankSlipTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "bank_slip_tax")
public class BankSlipTaxEntity extends BaseEntity {

    @Column
    private String code;

    @Column
    private BankSlipTypeEnum type;

    @Column(name = "original_value")
    private BigDecimal originalValue;

    @Column(name = "updated_value")
    private BigDecimal updatedValue;

    @Column
    private BigDecimal taxes;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

}
