package com.atlas.bank.account.service;


import com.atlas.bank.application.service.IAccountService;
import com.atlas.bank.domain.model.account.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Primary
public class AuditableAccountService implements IAccountService {

    private final IAccountService delegate;

    public AuditableAccountService(@Qualifier("accountService") IAccountService accountService) {
        this.delegate = accountService;
    }

    @Override
    public Account createAccount(Account account) {
        log.info("Creating account: {}", account);
        Account createdAccount = delegate.createAccount(account);
        log.info("Account created: {}", createdAccount);
        return createdAccount;
    }

    @Override
    public List<Account> findAll() {
        return delegate.findAll();
    }

    @Override
    public Account findById(Long id) {
        return delegate.findById(id);
    }
}