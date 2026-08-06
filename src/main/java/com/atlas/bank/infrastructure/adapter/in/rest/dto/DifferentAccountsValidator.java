package com.atlas.bank.infrastructure.adapter.in.rest.dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DifferentAccountsValidator implements ConstraintValidator<DifferentAccounts, TransferRequest> {

    @Override
    public boolean isValid(TransferRequest request, ConstraintValidatorContext constraintValidatorContext) {
        if (request.getToAccountId() == null || request.getFromAccountId() == null) {
            return true; // Let @NotNull handle null cases
        }
        return !request.getFromAccountId().equals(request.getToAccountId());
    }
}
