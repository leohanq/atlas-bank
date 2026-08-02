package com.atlas.bank.account.service;

import com.atlas.bank.account.dto.DashboardResponse;
import com.atlas.bank.account.model.Account;
import com.atlas.bank.transaction.dto.TransferResponse;
import com.atlas.bank.transaction.mapper.TransferMapper;
import com.atlas.bank.transaction.service.ITransactionQueryService;
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
                .balance(account.getBalance())
                .status(account.getStatus().name())
                .recentTransactions(transactions)
                .build();
    }
}
