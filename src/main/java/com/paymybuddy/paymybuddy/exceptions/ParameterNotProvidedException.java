package com.paymybuddy.paymybuddy.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
public class ParameterNotProvidedException extends Exception {
    private final String message = "Parameter not provided";
    private final HttpStatus status = HttpStatus.BAD_REQUEST;
}
