package com.atlas.bank.account.service;

import com.atlas.bank.account.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class CacheAccountService implements IAccountService {

    private final IAccountService delegate;
    private final Map<Long, Account> cache = new ConcurrentHashMap<>();

    public CacheAccountService(@Qualifier("auditableAccountService") IAccountService delegate) {
        this.delegate = delegate;
    }


    @Override
    public Account createAccount(Account account) {
        Account createdAccount = delegate.createAccount(account);
        cache.put(createdAccount.getId(), createdAccount);
        log.info("Adding account to cache: {}", createdAccount);
        return createdAccount;
    }

    @Override
    public List<Account> findAll() {
        return delegate.findAll();
    }

    @Override
    public Account findById(Long id) {
        if (cache.containsKey(id)) {
            log.info("Returning account from cache: {}", id);
            return cache.get(id);
        }
        Account account = delegate.findById(id);
        if (account != null) {
            cache.put(account.getId(), account);
            log.info("Adding account to cache: {}", account);
        }
        return account;
    }
}
