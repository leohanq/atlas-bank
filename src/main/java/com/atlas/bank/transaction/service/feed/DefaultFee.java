package com.atlas.bank.transaction.service.feed;

import com.atlas.bank.account.model.AccountType;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class DefaultFee implements FeedCalculator {

    @Override
    public boolean supports(AccountType accountType) {
        return true;
    }

    @Override
    public BigDecimal calculate(BigDecimal amount) {
        return BigDecimal.ZERO;
    }
}
