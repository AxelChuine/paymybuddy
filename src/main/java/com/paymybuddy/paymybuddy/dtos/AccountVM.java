package com.paymybuddy.paymybuddy.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode
public class AccountVM {
    private Long identifier;
    private String firstName;
    private String lastName;
    private String email;
    private String name;
    private BigDecimal balance;

    public  AccountVM(Long identifier) {
        this.identifier = identifier;
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.name = "";
        this.balance = BigDecimal.ZERO;
    }

    public AccountVM() {
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.name = "";
        this.balance = BigDecimal.ZERO;
    }

    public AccountVM(Long identifier, String firstName, String lastName, String email, String name, BigDecimal balance) {
        this.identifier = identifier;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.name = name;
        this.balance = balance;
    }

    public Long getIdentifier() {
        return identifier;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}