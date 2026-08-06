package com.atlas.bank.application.port.in;

import com.atlas.bank.application.command.TransferMoneyCommand;
import com.atlas.bank.domain.model.transaction.Transaction;

public interface TransferMoneyUseCase {

    Transaction execute(TransferMoneyCommand moneyCommand);
}
