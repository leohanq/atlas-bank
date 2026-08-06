package com.atlas.bank.domain.strategy.fee;

import com.atlas.bank.domain.model.account.AccountType;

import java.math.BigDecimal;

public class PremiumFeed implements FeedCalculator {
    @Override
    public boolean supports(AccountType accountType) {
        return "PREMIUM".equals(accountType);
    }

    @Override
    public BigDecimal calculate(BigDecimal amount) {
        return BigDecimal.ZERO;
    }
}
