package com.atlas.bank.transaction.controller;

import com.atlas.bank.transaction.dto.TransferRequest;
import com.atlas.bank.transaction.dto.TransferResponse;
import com.atlas.bank.transaction.mapper.TransferMapper;
import com.atlas.bank.transaction.model.Transaction;
import com.atlas.bank.transaction.service.ITransactionQueryService;
import com.atlas.bank.transaction.service.transfer.ITransferService;
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

    private final ITransactionQueryService transactionQueryService;
    private final ITransferService transferService;
    private final TransferMapper transferMapper;


    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transfer(@Valid @RequestBody TransferRequest request) {
        Transaction transfer = transferService.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(transferMapper.toResponse(transfer));
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransferResponse>> getTransactions(@PathVariable("id") Long accountId) {
        List<Transaction> transactions = transactionQueryService.getAccountById(accountId);
        return ResponseEntity.ok(transferMapper.toResponseList(transactions));
    }
}
