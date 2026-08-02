package com.atlas.bank.transaction.validation.chain;

import com.atlas.bank.account.exception.AccountNotActiveException;
import com.atlas.bank.account.model.AccountStatus;
import com.atlas.bank.transaction.service.transfer.TransferContext;
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
