package com.atlas.bank.transaction.service.transfer;

import com.atlas.bank.domain.exception.AccountNotFoundException;
import com.atlas.bank.domain.model.account.Account;
import com.atlas.bank.application.in.TransferMoneyUseCase;
import com.atlas.bank.application.out.AccountRepositoryPort;
import com.atlas.bank.domain.model.transaction.TransferContext;
import com.atlas.bank.transaction.dto.TransferRequest;
import com.atlas.bank.domain.model.transaction.Transaction;
import com.atlas.bank.transaction.repository.TransactionRepository;
import com.atlas.bank.domain.service.TransferDomainService;
import com.atlas.bank.transaction.service.factory.TransactionFactory;
import com.atlas.bank.domain.strategy.fee.FeedCalculator;
import com.atlas.bank.domain.validation.TransferValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransferService extends TransactionProcessor<TransferContext> implements TransferMoneyUseCase {


    private final AccountRepositoryPort accountRepository;
    private final List<FeedCalculator> feedCalculators;
    private final List<TransferValidator> validators;
    private final TransferDomainService transferDomainService;


    public TransferService(TransactionRepository transactionRepository, AccountRepositoryPort accountRepository, List<FeedCalculator> feedCalculators,
                            List<TransferValidator> validators, TransferDomainService transferDomainService) {
            super(transactionRepository);
            this.accountRepository = accountRepository;
            this.feedCalculators = feedCalculators;
            this.validators = validators;
        this.transferDomainService = transferDomainService;
    }

    @Transactional
    @Override
    public Transaction execute(TransferRequest request) {
        // Buscar cuentas
        Account from = accountRepository.findById(request.getFromAccountId())
                .orElseThrow(() -> new AccountNotFoundException(request.getFromAccountId()));
        Account to = accountRepository.findById(request.getToAccountId())
                .orElseThrow(() -> new AccountNotFoundException(request.getToAccountId()));

        TransferContext transaction = new TransferContext(from, to,
                request.getAmount()
        );
        Transaction process = process(transaction);

        process.executeTransfer();

        transactionRepository.save(process);
        return process;
    }

    @Override
    protected void validate(TransferContext context) {
        validators.forEach(v -> v.validate(context));
    }

    @Override
    protected BigDecimal calculateFeed(TransferContext context) {
        // Calcular comisión — hardcodeada
        BigDecimal fee = BigDecimal.ZERO;

        for (FeedCalculator calculator : feedCalculators) {
            if (calculator.supports(context.from().getType())) {
                fee = calculator.calculate(context.amount());
            }
        }
        return fee;
    }

    @Override
    protected void execute(TransferContext context, BigDecimal fee) {
        transferDomainService.transfer(context.from(), context.to(), context.amount(), fee);
    }

    @Override
    protected Transaction save(TransferContext context, BigDecimal fee) {
        Transaction transaction = TransactionFactory.createTransaction(context, fee);
        return transactionRepository.save(transaction);
    }
}
