package com.atlas.bank.account.repository;

import com.atlas.bank.account.model.Account;

import java.util.List;
import java.util.Optional;

public interface DomainAccountRepository {

    Optional<Account> findById(Long accountId);

    List<Account> findAll();

    Account save(Account account);
}
