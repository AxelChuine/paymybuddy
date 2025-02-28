package com.paymybuddy.paymybuddy.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private Long identifier;
    private String name;
    private BigDecimal amount;
    private AccountDto sender;
    private AccountDto recipient;
    private LocalDateTime transactionDate;
}
