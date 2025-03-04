package com.bank.adapter.datasource.integration.database.repository;

import com.bank.adapter.datasource.integration.database.entity.BankSlipTaxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankSlipTaxRepository extends JpaRepository<BankSlipTaxEntity, Long> {
}
