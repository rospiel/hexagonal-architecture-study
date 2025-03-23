package com.bank.adapter.datasource.integration.database.repository;

import com.bank.adapter.datasource.integration.database.entity.BankSlipTaxEntity;
import com.bank.config.IntegrationTestConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;


@DataJpaTest
/* Uses our own database config in application.yml */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BankSlipTaxRepositoryIT extends IntegrationTestConfig {

    @Autowired
    private BankSlipTaxRepository repository;

    @Test
    @DisplayName("findAll should return all bank slip tax executed")
    @Order(1)
    @Sql("/test/sql/insert_bank_slip_tax.sql")
    void findAll_ReturnAllBankSlipTaxExecuted_WhenSuccessful() {
        List<BankSlipTaxEntity> all = repository.findAll();

        Assertions.assertThat(all).isNotEmpty();
    }

}