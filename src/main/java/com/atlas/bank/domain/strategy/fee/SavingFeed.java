package com.atlas.bank.transaction.service.feed;

import com.atlas.bank.domain.model.account.AccountType;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Order(1)
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
