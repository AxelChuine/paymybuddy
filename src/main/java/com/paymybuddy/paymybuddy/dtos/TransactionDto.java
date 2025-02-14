package com.paymybuddy.paymybuddy.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
    private AccountVM sender;
    private AccountVM recipient;
    private LocalDateTime transactionDate;
}
