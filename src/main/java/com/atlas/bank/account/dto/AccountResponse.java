package com.atlas.bank.account.dto;

import com.atlas.bank.account.model.AccountStatus;
import com.atlas.bank.account.model.AccountType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AccountResponse {

    private Long id;
    private String accountNumber;
    private String ownerName;
    private String email;
    private AccountType type; // SAVING, CHECKING
    private BigDecimal balance;
    private AccountStatus status; // ACTIVE, FROZEN, CLOSED
    private LocalDateTime createdAt;
}
