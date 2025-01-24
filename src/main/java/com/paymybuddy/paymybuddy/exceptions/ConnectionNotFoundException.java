package com.paymybuddy.paymybuddy.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper=true)
public class ConnectionNotFoundException extends Exception {
    private final String message = "Connection not found";
    private final HttpStatus status = HttpStatus.NOT_FOUND;
}
