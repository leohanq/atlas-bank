package com.atlas.bank.transaction.service.feed;

import com.atlas.bank.domain.model.account.AccountType;

import java.math.BigDecimal;

public interface FeedCalculator {

    boolean supports(AccountType accountType);
    BigDecimal calculate(BigDecimal amount);
}
