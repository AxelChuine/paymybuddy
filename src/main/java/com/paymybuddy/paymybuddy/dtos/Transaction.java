package com.paymybuddy.paymybuddy.dtos;

import java.math.BigDecimal;

public class Transaction {
    private int identifier;
    private String name;
    private BigDecimal amount;
    private AccountDto sender;
    private AccountDto recipient;
}