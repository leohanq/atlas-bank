package com.atlas.bank.transaction.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {

    @NotNull(message = "Source account ID cannot be null")
    private Long fromAccountId;
    @NotNull(message = "Target account ID cannot be null")
    private Long toAccountId;
    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

}
