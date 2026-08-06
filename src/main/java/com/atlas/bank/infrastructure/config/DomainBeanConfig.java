package com.atlas.bank.infrastructure.config;

import com.atlas.bank.application.port.out.FraudCheckPort;
import com.atlas.bank.application.validation.FraudValidator;
import com.atlas.bank.domain.service.TransferDomainService;
import com.atlas.bank.domain.strategy.fee.*;
import com.atlas.bank.domain.validation.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class DomainBeanConfig {

    @Bean
    public TransferDomainService transferDomainService() {
        return new TransferDomainService();
    }

    @Bean @Order(1)
    public SavingFeed savingsFeeCalculator() {
        return new SavingFeed();
    }

    @Bean @Order(2)
    public CheckingFeed checkingFeeCalculator() {
        return new CheckingFeed();
    }

    @Bean @Order(3)
    public PremiumFeed premiumFeeCalculator() {
        return new PremiumFeed();
    }

    @Bean
    public DefaultFee defaultFeeCalculator() {
        return new DefaultFee();
    }

    @Bean @Order(1)
    public AccountStatusValidator accountStatusValidator() {
        return new AccountStatusValidator();
    }

    @Bean @Order(2)
    public SufficientFundsValidator sufficientFundsValidator() {
        return new SufficientFundsValidator();
    }

    @Bean @Order(3)
    public FraudValidator fraudValidator(FraudCheckPort fraudCheckPort) {
        return new FraudValidator(fraudCheckPort);
    }
}