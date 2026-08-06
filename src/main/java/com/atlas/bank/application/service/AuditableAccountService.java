package com.atlas.bank.application.service;


import com.atlas.bank.application.port.in.CreateAccountUseCase;
import com.atlas.bank.application.port.in.GetAccountUseCase;
import com.atlas.bank.application.port.in.ListAccountsUseCase;
import com.atlas.bank.domain.model.account.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Primary
public class AuditableAccountService implements CreateAccountUseCase, GetAccountUseCase, ListAccountsUseCase {

    private final CreateAccountUseCase createAccount;
    private final GetAccountUseCase getAccountUseCase;
    private final ListAccountsUseCase listAccountsUseCase;

    public AuditableAccountService(@Qualifier("accountService") CreateAccountUseCase createAccountUseCase,
                                   @Qualifier("accountService") ListAccountsUseCase listAccountsUseCase,
                                   @Qualifier("accountService") GetAccountUseCase getAccountUseCase) {
        this.createAccount = createAccountUseCase;
        this.getAccountUseCase = getAccountUseCase;
        this.listAccountsUseCase = listAccountsUseCase;
    }

    @Override
    public Account execute(Account account) {
        log.info("Creating account: {}", account);
        Account createdAccount = createAccount.execute(account);
        log.info("Account created: {}", createdAccount);
        return createdAccount;
    }

    @Override
    public List<Account> findAll() {
        return listAccountsUseCase.findAll();
    }

    @Override
    public Account findById(Long id) {
        return getAccountUseCase.findById(id);
    }
}