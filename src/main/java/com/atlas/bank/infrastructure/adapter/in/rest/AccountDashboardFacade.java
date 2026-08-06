package com.atlas.bank.infrastructure.adapter.in.rest;

import com.atlas.bank.application.port.in.GetAccountUseCase;
import com.atlas.bank.application.port.in.GetTransactionByAccountUseCase;
import com.atlas.bank.domain.model.account.Account;
import com.atlas.bank.infrastructure.adapter.in.rest.dto.DashboardResponse;
import com.atlas.bank.infrastructure.adapter.in.rest.dto.TransferMapper;
import com.atlas.bank.infrastructure.adapter.in.rest.dto.TransferResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountDashboardFacade {

    private final GetAccountUseCase AccountUseCase;
    private final GetTransactionByAccountUseCase transactionUseCase;
    private final TransferMapper transactionMapper;

    public DashboardResponse getDashboard(Long accountId) {
        Account account = AccountUseCase.findById(accountId);

        List<TransferResponse> transactions = transactionUseCase.getAccountById(accountId)
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
