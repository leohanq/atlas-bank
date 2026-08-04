package com.atlas.bank.transaction.service.fraud;

public record FraudCheckResult(boolean blocked, String risk) {

    public static FraudCheckResult allowed() {
        return new FraudCheckResult(false, null);
    }

    public static FraudCheckResult blocked(String reason) {
        return new FraudCheckResult(true, reason);
    }
}
