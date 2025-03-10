package com.paymybuddy.paymybuddy.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long identifier;
    private String firstName;
    private String lastName;
    private String password;
    @NotEmpty(message = "The email is required")
    @Email
    private String email;
    private String name;
    private BigDecimal balance;
    private Set<AccountDto> connectionDtos;
}
