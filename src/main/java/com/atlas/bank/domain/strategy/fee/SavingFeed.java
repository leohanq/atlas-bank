package com.atlas.bank.domain.strategy.fee;

import com.atlas.bank.domain.model.account.AccountType;

import java.math.BigDecimal;

public class SavingFeed implements FeedCalculator {

    private static final BigDecimal SAVING_FEE_PERCENTAGE = new BigDecimal("0.01");

    @Override
    public boolean supports(AccountType accountType) {
        return AccountType.SAVING == accountType;
    }

    @Override
    public BigDecimal calculate(BigDecimal amount) {
        return amount.multiply(SAVING_FEE_PERCENTAGE);
    }
}
