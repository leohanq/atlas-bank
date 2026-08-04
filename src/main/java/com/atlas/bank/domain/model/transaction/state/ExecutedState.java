package com.atlas.bank.transaction.model.state;

import com.atlas.bank.domain.model.transaction.TransactionStatus;

public record ExecutedState() implements TransactionState {
    @Override
    public TransactionStatus status() {
        return TransactionStatus.EXECUTED;
    }
}
