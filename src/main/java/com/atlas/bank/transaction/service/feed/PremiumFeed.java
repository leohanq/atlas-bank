package com.atlas.bank.transaction.service.feed;

import com.atlas.bank.account.model.AccountType;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Order(1)
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
