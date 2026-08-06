package com.atlas.bank.infrastructure.adapter.in.rest;

import com.atlas.bank.application.command.TransferMoneyCommand;
import com.atlas.bank.application.port.in.GetTransactionByAccountUseCase;
import com.atlas.bank.application.port.in.TransferMoneyUseCase;
import com.atlas.bank.domain.model.transaction.Transaction;
import com.atlas.bank.infrastructure.adapter.in.rest.dto.TransferMapper;
import com.atlas.bank.infrastructure.adapter.in.rest.dto.TransferRequest;
import com.atlas.bank.infrastructure.adapter.in.rest.dto.TransferResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final GetTransactionByAccountUseCase transactionUseCase;
    private final TransferMoneyUseCase useCase;
    private final TransferMapper transferMapper;


    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transfer(@Valid @RequestBody TransferRequest request) {
        TransferMoneyCommand command = TransferMoneyCommand.builder()
                .toId(request.getToAccountId())
                .fromId(request.getFromAccountId())
                .amount(request.getAmount())
                .build();
        Transaction transfer = useCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(transferMapper.toResponse(transfer));
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransferResponse>> getTransactions(@PathVariable("id") Long accountId) {
        List<Transaction> transactions = transactionUseCase.getAccountById(accountId);
        return ResponseEntity.ok(transferMapper.toResponseList(transactions));
    }
}
