package com.atlas.bank.transaction.service.fraud;

import com.atlas.bank.application.out.FraudCheckPort;
import com.atlas.bank.domain.model.shared.FraudCheckResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class ExternalFraudCheckAdapter implements FraudChecker, FraudCheckPort {


    @Override
    public FraudCheckResult check(Long accountId, BigDecimal amount) {
        ExternalFraudResponse response = callExternalApi(accountId, amount);
        log.info("Response from external fraud checker: {}", response);
        if ("BLOCKED".equalsIgnoreCase(response.getRecommendation())) {
            return FraudCheckResult.blocked(response.getRiskLevel());
        }
        return FraudCheckResult.allowed();
    }

    private ExternalFraudResponse callExternalApi(Long accountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.valueOf(10000000)) > 0) {
            return new ExternalFraudResponse("HIGH", 0.95, "BLOCKED");
        }
        return new ExternalFraudResponse("LOW", 0.1, "ALLOW");
    }
}
