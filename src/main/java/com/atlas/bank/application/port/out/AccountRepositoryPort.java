package com.atlas.bank.application.out;

import com.atlas.bank.domain.model.account.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepositoryPort {

    Optional<Account> findById(Long accountId);

    List<Account> findAll();

    Account save(Account account);
}