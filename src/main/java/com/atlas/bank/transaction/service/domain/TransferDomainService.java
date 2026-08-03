package com.atlas.bank.transaction.service.domain;

import com.atlas.bank.account.exception.InsufficientFundsException;
import com.atlas.bank.account.model.Account;
import com.atlas.bank.shared.model.Money;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransferDomainService {

    public void transfer(Account sourceAccount, Account targetAccount, BigDecimal amount, BigDecimal fee) {
        Money totalAmount = Money.of(amount.add(fee), sourceAccount.getBalance().getCurrency());

        Money deposit = Money.of(amount, targetAccount.getBalance().getCurrency());

        sourceAccount.withdraw(totalAmount);
        targetAccount.deposit(deposit);

    }

}
