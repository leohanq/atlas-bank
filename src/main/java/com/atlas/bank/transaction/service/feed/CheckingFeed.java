package com.atlas.bank.transaction.service.feed;

import com.atlas.bank.account.model.AccountType;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Order(1)
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