package com.atlas.bank.transaction.model.state;

import com.atlas.bank.domain.model.transaction.TransactionStatus;

public record RejectedState() implements TransactionState {
    @Override
    public TransactionStatus status() {
        return TransactionStatus.REJECTED;
    }
}
