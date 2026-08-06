package com.atlas.bank.infrastructure.adapter.out.persistence;

import com.atlas.bank.application.port.out.TransactionRepositoryPort;
import com.atlas.bank.domain.model.transaction.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaTransactionRepositoryAdapter implements TransactionRepositoryPort {

    private final SpringDataTransactionRepository transactionRepository;
    private final TransactionPersistenceMapper transactionPersistenceMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Transaction save(Transaction transaction) {
        transaction.initDefaults();
        TransactionJpaEntity jpaEntity = transactionPersistenceMapper.toJpaEntity(transaction);
        TransactionJpaEntity savedEntity = transactionRepository.save(jpaEntity);
        Transaction domain = transactionPersistenceMapper.toDomain(savedEntity);
        transaction.getDomainEvents().forEach(eventPublisher::publishEvent);
        transaction.clearDomainEvents();
        return domain;
    }

    @Override
    public List<Transaction> findBySourceAccountIdOrTargetAccountId(Long sourceId, Long targetId) {
        return transactionRepository.findBySourceAccountIdOrTargetAccountId(sourceId, targetId)
                .stream()
                .map(transactionPersistenceMapper::toDomain)
                .toList();
    }
}
