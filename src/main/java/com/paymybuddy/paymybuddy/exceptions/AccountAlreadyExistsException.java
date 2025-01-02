package com.paymybuddy.paymybuddy.exceptions;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
public class AccountAlreadyExistsException extends Exception{
    private final String message = "Account already exists";
    private final HttpStatus status = HttpStatus.CONFLICT;
}
