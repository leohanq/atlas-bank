package com.atlas.bank.infrastructure.adapter.out.persistence;

import com.atlas.bank.domain.model.account.Account;
import com.atlas.bank.domain.model.shared.Currency;
import com.atlas.bank.domain.model.shared.Money;
import org.springframework.stereotype.Component;

@Component
public class AccountPersistenceMapper {

    public Account toDomain(AccountJpaEntity entity) {
        if (entity == null) return null;
        return Account.builder()
                .id(entity.getId())
                .accountNumber(entity.getAccountNumber())
                .ownerName(entity.getOwnerName())
                .email(entity.getEmail() != null
                        ? com.atlas.bank.domain.model.shared.Email.of(entity.getEmail().getValue())
                        : null)
                .type(entity.getType())
                .balance(entity.getBalance() != null
                        ? Money.of(
                        entity.getBalance().getAmount(),
                        Currency.valueOf(
                                entity.getBalance().getCurrency().name()))
                        : null)
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .customerId(entity.getCustomerId())
                .build();
    }

    public AccountJpaEntity toJpaEntity(Account account) {
        if (account == null) return null;
        AccountJpaEntity entity = new AccountJpaEntity();
        entity.setId(account.getId());
        entity.setAccountNumber(account.getAccountNumber());
        entity.setOwnerName(account.getOwnerName());
        entity.setEmail(account.getEmail() != null
                ? com.atlas.bank.domain.model.shared.Email.of(account.getEmail().getValue())
                : null);
        entity.setType(account.getType());
        entity.setBalance(account.getBalance() != null
                ? com.atlas.bank.infrastructure.adapter.out.persistence.Money.of(
                account.getBalance().getAmount(),
                Currency.valueOf(
                        account.getBalance().getCurrency().name()))


                : null);
        entity.setStatus(account.getStatus());
        entity.setCreatedAt(account.getCreatedAt());
        entity.setCustomerId(account.getCustomerId());
        return entity;
    }
}