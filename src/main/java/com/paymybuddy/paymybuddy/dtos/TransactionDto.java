package com.paymybuddy.paymybuddy.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TransactionDto {
    private Long identifier;
    private String name;
    private BigDecimal amount;
    private AccountDto sender;
    private AccountDto recipient;
}