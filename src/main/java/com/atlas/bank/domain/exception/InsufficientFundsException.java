package com.atlas.bank.account.exception;

import java.math.BigDecimal;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(Long accountId, BigDecimal balance, BigDecimal amount) {
        super(String.format("Insufficient funds in account %d. Current balance: %s, attempted withdrawal: %s", accountId, balance, amount));
    }
}
