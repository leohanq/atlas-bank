package com.atlas.bank.infrastructure.adapter.out.persistence;

import com.atlas.bank.application.port.out.AccountRepositoryPort;
import com.atlas.bank.domain.model.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaAccountRepositoryAdapter implements AccountRepositoryPort {

    private final SpringDataAccountRepository springDataAccountRepository;
    private final AccountPersistenceMapper accountPersistenceMapper;


    @Override
    public Optional<Account> findById(Long accountId) {
        return springDataAccountRepository.findById(accountId)
                .map(accountPersistenceMapper::toDomain);
    }

    @Override
    public List<Account> findAll() {
        return springDataAccountRepository.findAll()
                .stream()
                .map(accountPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Account save(Account account) {
        account.initDefaults();
        return accountPersistenceMapper.toDomain(
                springDataAccountRepository.save(
                        accountPersistenceMapper.toJpaEntity(account)));
    }
}