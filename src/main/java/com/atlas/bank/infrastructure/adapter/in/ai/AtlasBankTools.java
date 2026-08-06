package com.atlas.bank.infrastructure.adapter.in.ai;

import com.atlas.bank.application.command.TransferMoneyCommand;
import com.atlas.bank.application.port.in.GetAccountUseCase;
import com.atlas.bank.application.port.in.TransferMoneyUseCase;
import com.atlas.bank.domain.exception.InsufficientFundsException;
import com.atlas.bank.domain.model.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class AtlasBankTools {

    private final TransferMoneyUseCase transferMoneyUseCase;
    private final GetAccountUseCase getAccountUseCase;

    @Tool(description = "Transfer money from one account to another")
    public String transferMoney(@ToolParam(description = "Id of the account to transfer money from")
                                String sourceAccountId,
                                @ToolParam(description = "Id of the account to transfer money to")
                                String targetAccountId,
                                @ToolParam(description = "Amount of money to transfer")
                                BigDecimal amount) {

        try {
            var command = TransferMoneyCommand.builder()
                    .fromId(Long.parseLong(sourceAccountId))
                    .toId(Long.parseLong(targetAccountId))
                    .amount(amount)
                    .build();
            transferMoneyUseCase.execute(command);
            return "Money transferred successfully from account " + sourceAccountId + " to account " + targetAccountId;
        } catch (InsufficientFundsException e) {
            return "Insufficient funds in source account";
        }
        catch (Exception e) {
            return "Error transferring money: " + e.getMessage();
        }
    }

    @Tool(description = "Get account details by account id")
    public String getAccount(@ToolParam(description = "Id of the account to get") String accountId) {
        try {
            Account account = getAccountUseCase.findById(Long.parseLong(accountId));
            return "Account ID: " + account.getId() + ", Balance: " + account.getBalance();
        } catch (Exception e) {
            return "Error retrieving account: " + e.getMessage();
        }
    }
}
