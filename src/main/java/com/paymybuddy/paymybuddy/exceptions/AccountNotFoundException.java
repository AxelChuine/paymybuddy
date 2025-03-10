package com.paymybuddy.paymybuddy.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
public class AccountNotFoundException extends Exception{
    private final String message = "No account found";
    private final HttpStatus status = HttpStatus.NOT_FOUND;

}
