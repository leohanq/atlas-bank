package com.atlas.bank.transaction.model.state;

import com.atlas.bank.transaction.model.TransactionStatus;

public sealed interface TransactionState permits PendingState, ValidatedState,
        ExecutedState, RejectedState, ReversedState {

    TransactionStatus status();

    default TransactionState validate() {
        throw new RuntimeException("not implemented for status " + status());
    }

    default TransactionState execute() {
        throw new RuntimeException("not implemented for status " + status());
    }

    default TransactionState reject() {
        throw new RuntimeException("not implemented for status " + status());
    }

    default TransactionState reverse() {
        throw new RuntimeException("not implemented for status " + status());
    }


}
