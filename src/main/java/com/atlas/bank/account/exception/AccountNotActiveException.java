package com.atlas.bank.account.exception;

import com.atlas.bank.account.model.AccountStatus;

public class AccountNotActiveException extends RuntimeException {
    public AccountNotActiveException(Long accountId, AccountStatus status) {
        super("Account with ID " + accountId + " is not active. Current status: " + status);
    }
}
