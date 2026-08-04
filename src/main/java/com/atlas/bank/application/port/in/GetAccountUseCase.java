package com.atlas.bank.application.in;

import com.atlas.bank.domain.model.account.Account;

public interface GetAccountUseCase {

    Account findById(Long id);
}
