package com.atlas.bank.transaction.validation.chain;

import com.atlas.bank.domain.model.transaction.TransferContext;

public interface TransferValidator {

    void validate(TransferContext context);
}
