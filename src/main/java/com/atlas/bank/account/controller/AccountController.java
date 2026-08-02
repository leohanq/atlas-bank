package com.atlas.bank.account.controller;

import com.atlas.bank.account.dto.AccountResponse;
import com.atlas.bank.account.dto.CreateAccountRequest;
import com.atlas.bank.account.dto.DashboardResponse;
import com.atlas.bank.account.mapper.AccountMapper;
import com.atlas.bank.account.model.Account;
import com.atlas.bank.account.service.AccountDashboardFacade;
import com.atlas.bank.account.service.IAccountService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final IAccountService accountService;
    private final AccountMapper accountMapper;
    private final AccountDashboardFacade accountDashboardFacade;
    

    @GetMapping("/{id}/dashboard")
    public ResponseEntity<DashboardResponse> getDashboard(@PathVariable("id") Long accountId) {
        return ResponseEntity.ok(accountDashboardFacade.getDashboard(accountId));
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        Account account = accountMapper.toEntity(request);
        Account saved = accountService.createAccount(account);
        AccountResponse response = accountMapper.toResponse(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> findAll() {
        List<Account> accountsEntity = accountService.findAll();
        List<AccountResponse> accounts = accountMapper.accountResponseList(accountsEntity);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> findById(@PathVariable("id") Long id) {
        Account accountEntity = accountService.findById(id);
        AccountResponse account = accountMapper.toResponse(accountEntity);
        return ResponseEntity.ok(account);
    }
}