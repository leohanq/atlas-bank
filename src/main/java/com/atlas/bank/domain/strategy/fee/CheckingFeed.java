package com.atlas.bank.domain.strategy.fee;

import com.atlas.bank.domain.model.account.AccountType;

import java.math.BigDecimal;

public class CheckingFeed implements FeedCalculator {

    private static final double CHECKING_FEE_PERCENTAGE = 0.02;

    @Override
    public boolean supports(AccountType accountType) {
        return AccountType.CHECKING ==accountType;
    }

    @Override
    public BigDecimal calculate(BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(CHECKING_FEE_PERCENTAGE));
    }
}