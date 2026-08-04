package com.atlas.bank.transaction.validation.chain;

import com.atlas.bank.domain.exception.FraudCheckException;
import com.atlas.bank.domain.model.shared.FraudCheckResult;
import com.atlas.bank.transaction.service.fraud.FraudChecker;
import com.atlas.bank.domain.model.transaction.TransferContext;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
@RequiredArgsConstructor
public class FraudValidator implements TransferValidator {

    private final FraudChecker fraudChecker;

    @Override
    public void validate(TransferContext context) {
        FraudCheckResult fraudCheckResult = fraudChecker.check(context.from().getId(), context.amount());
        if (fraudCheckResult.blocked()) {
            throw new FraudCheckException(fraudCheckResult.risk());
        }
    }
}
