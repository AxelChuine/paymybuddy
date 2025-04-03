package com.paymybuddy.paymybuddy.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AccountDto {
    private Long identifier;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String name;
    private BigDecimal balance;
    private Set<AccountDto> connectionDtos;
}
