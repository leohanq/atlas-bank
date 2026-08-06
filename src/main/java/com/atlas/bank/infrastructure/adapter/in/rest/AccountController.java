package com.atlas.bank.infrastructure.adapter.in.rest;

import com.atlas.bank.application.command.CloseAccountCommand;
import com.atlas.bank.application.port.in.CloseAccountUseCase;
import com.atlas.bank.application.port.in.CreateAccountUseCase;
import com.atlas.bank.application.port.in.GetAccountUseCase;
import com.atlas.bank.application.port.in.ListAccountsUseCase;
import com.atlas.bank.domain.model.account.Account;
import com.atlas.bank.infrastructure.adapter.in.rest.dto.AccountMapper;
import com.atlas.bank.infrastructure.adapter.in.rest.dto.AccountResponse;
import com.atlas.bank.infrastructure.adapter.in.rest.dto.CreateAccountRequest;
import com.atlas.bank.infrastructure.adapter.in.rest.dto.DashboardResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final CreateAccountUseCase createAccount;
    private final GetAccountUseCase getAccountUseCase;
    private final ListAccountsUseCase listAccountsUseCase;
    private final CloseAccountUseCase closeAccountUseCase;
    private final AccountMapper accountMapper;
    private final AccountDashboardFacade accountDashboardFacade;
    

    @GetMapping("/{id}/dashboard")
    public ResponseEntity<DashboardResponse> getDashboard(@PathVariable("id") Long accountId) {
        return ResponseEntity.ok(accountDashboardFacade.getDashboard(accountId));
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        Account account = accountMapper.toEntity(request);
        Account saved = createAccount.execute(account);
        AccountResponse response = accountMapper.toResponse(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> findAll() {
        List<Account> accountsEntity = listAccountsUseCase.findAll();
        List<AccountResponse> accounts = accountMapper.accountResponseList(accountsEntity);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> findById(@PathVariable("id") Long id) {
        Account accountEntity = getAccountUseCase.findById(id);
        AccountResponse account = accountMapper.toResponse(accountEntity);
        return ResponseEntity.ok(account);
    }

    @PatchMapping("/{id}/close")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountResponse> closeAccount(@PathVariable("id") Long id) {
        Account closed = closeAccountUseCase.close(new CloseAccountCommand(id));
        return ResponseEntity.ok(accountMapper.toResponse(closed));
    }
}