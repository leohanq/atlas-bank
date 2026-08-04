package com.atlas.bank.transaction.validation.chain;

import com.atlas.bank.domain.exception.AccountNotActiveException;
import com.atlas.bank.domain.model.account.AccountStatus;
import com.atlas.bank.domain.model.transaction.TransferContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class AccountStatusValidator implements TransferValidator {
    @Override
    public void validate(TransferContext context) {
        if (AccountStatus.ACTIVE != context.from().getStatus()) {
            throw new AccountNotActiveException(context.from().getId(), context.from().getStatus());
        }
        if (AccountStatus.ACTIVE != context.to().getStatus()) {
            throw new AccountNotActiveException(context.to().getId(), context.to().getStatus());
        }
    }
}
