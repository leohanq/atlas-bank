package com.atlas.bank.domain.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(Long id) {
        super("No se encontro la cuenta con id " + id);
    }
}
