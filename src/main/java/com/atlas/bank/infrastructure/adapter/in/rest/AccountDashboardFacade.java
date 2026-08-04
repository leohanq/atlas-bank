package com.atlas.bank.application.service;

import com.atlas.bank.infrastructure.adapter.in.rest.dto.DashboardResponse;
import com.atlas.bank.domain.model.account.Account;
import com.atlas.bank.infrastructure.adapter.in.rest.dto.TransferResponse;
import com.atlas.bank.infrastructure.adapter.in.rest.dto.TransferMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountDashboardFacade {

    private final IAccountService accountService;
    private final ITransactionQueryService transactionQueryService;
    private final TransferMapper transactionMapper;

    public DashboardResponse getDashboard(Long accountId) {
        Account account = accountService.findById(accountId);

        List<TransferResponse> transactions = transactionQueryService.getAccountById(accountId)
                .stream()
                .map(transactionMapper::toResponse)
                .toList();

        return DashboardResponse.builder()
                .accountID(account.getId())
                .accountNumber(account.getAccountNumber())
                .ownerName(account.getOwnerName())
                .type(account.getType().name())
                .balance(account.getBalance().getAmount())
                .status(account.getStatus().name())
                .recentTransactions(transactions)
                .build();
    }
}
