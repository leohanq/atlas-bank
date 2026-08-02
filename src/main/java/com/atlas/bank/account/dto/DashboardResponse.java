package com.atlas.bank.account.dto;

import com.atlas.bank.transaction.dto.TransferResponse;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class DashboardResponse {

    private Long accountID;
    private String accountNumber;
    private String ownerName;
    private String type;
    private BigDecimal balance;
    private String status;
    private List<TransferResponse> recentTransactions;
}
