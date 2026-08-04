package com.atlas.bank.application.out;

import com.atlas.bank.domain.model.shared.FraudCheckResult;

import java.math.BigDecimal;

public interface FraudCheckPort {

    FraudCheckResult check(Long accountId, BigDecimal amount);
}