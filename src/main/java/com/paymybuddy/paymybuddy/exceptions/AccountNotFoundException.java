package com.paymybuddy.paymybuddy.exceptions;

import org.springframework.http.HttpStatus;

public class AccountNotFoundException extends Exception{
    private String message;
    private HttpStatus status;

    public AccountNotFoundException(String message) {
        super(message);
        this.message = message;
        this.status = HttpStatus.NOT_FOUND;
    }
}
