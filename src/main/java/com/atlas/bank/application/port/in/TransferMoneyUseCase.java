package com.atlas.bank.application.in;

import com.atlas.bank.infrastructure.adapter.in.rest.dto.TransferRequest;
import com.atlas.bank.domain.model.transaction.Transaction;

public interface TransferMoneyUseCase {

    Transaction execute(TransferRequest request);
}
