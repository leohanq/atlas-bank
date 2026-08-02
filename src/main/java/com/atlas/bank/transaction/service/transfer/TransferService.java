package com.atlas.bank.transaction.service.transfer;

import com.atlas.bank.account.exception.AccountNotFoundException;
import com.atlas.bank.account.model.Account;
import com.atlas.bank.account.repository.AccountRepository;
import com.atlas.bank.transaction.dto.TransferRequest;
import com.atlas.bank.transaction.model.Transaction;
import com.atlas.bank.transaction.repository.TransactionRepository;
import com.atlas.bank.transaction.service.event.TransactionExecutedEvent;
import com.atlas.bank.transaction.service.factory.TransactionFactory;
import com.atlas.bank.transaction.service.feed.FeedCalculator;
import com.atlas.bank.transaction.validation.chain.TransferValidator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransferService extends TransactionProcessor<TransferContext> implements ITransferService {

    private final AccountRepository accountRepository;
    private final List<FeedCalculator> feedCalculators;
    private final ApplicationEventPublisher publisher;
    private final List<TransferValidator> validators;

    public TransferService(TransactionRepository transactionRepository, AccountRepository accountRepository, List<FeedCalculator> feedCalculators,
                           ApplicationEventPublisher publisher, List<TransferValidator> validators) {
        super(transactionRepository);
        this.accountRepository = accountRepository;
        this.feedCalculators = feedCalculators;
        this.publisher = publisher;
        this.validators = validators;
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

        process.advanceToNextState(process.getState().validate());
        process.advanceToNextState(process.getState().execute());
        transactionRepository.save(process);

        publisher.publishEvent(new TransactionExecutedEvent(process.getId(), process.getType(),
                process.getSourceAccountId(), process.getTargetAccountId(), process.getAmount(), process.getFee()));

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
        // Actualizar saldos
        context.from().setBalance(context.from().getBalance().subtract(context.amount()).subtract(fee));
        context.to().setBalance(context.to().getBalance().add(context.amount()));
        accountRepository.save(context.from());
        accountRepository.save(context.to());
    }

    @Override
    protected Transaction save(TransferContext context, BigDecimal fee) {
        Transaction transaction = TransactionFactory.createTransaction(context, fee);
        return transactionRepository.save(transaction);
    }
}
