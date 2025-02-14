package com.paymybuddy.paymybuddy.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode
public class TransactionToDisplay {
    private Long id;
    private String firstName;
    private String description;
    private BigDecimal amount;

    public TransactionToDisplay() {
        this.id = 0L;
        this.firstName = "";
        this.description = "";
        this.amount = BigDecimal.ZERO;
    }

    public TransactionToDisplay(Long id, String firstName, String description, BigDecimal amount) {
        this.id = id;
        this.firstName = firstName;
        this.description = description;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
