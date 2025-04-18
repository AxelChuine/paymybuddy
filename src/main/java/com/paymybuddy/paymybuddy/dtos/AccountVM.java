package com.paymybuddy.paymybuddy.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountVM {
    private Long identifier;
    private String firstName;
    private String lastName;
    private String email;
    private String name;
    private BigDecimal balance;
}