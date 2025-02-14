package com.paymybuddy.paymybuddy.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode
public class AccountDto {
    private Long identifier;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String name;
    private BigDecimal balance;
    private Set<AccountDto> connectionDtos;

    public AccountDto() {
        this.identifier = 0L;
        this.firstName = "";
        this.lastName = "";
        this.password = "";
        this.email = "";
        this.name = "";
        this.balance = BigDecimal.ZERO;
        this.connectionDtos = new HashSet<AccountDto>();
    }

    public AccountDto(Long identifier, String firstName, String lastName, String password, String email, String name, BigDecimal balance) {
        this.identifier = identifier;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.name = name;
        this.balance = balance;
        this.connectionDtos = new HashSet<AccountDto>();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Set<AccountDto> getConnectionDtos() {
        return connectionDtos;
    }

    public void setConnectionDtos(Set<AccountDto> connectionDtos) {
        this.connectionDtos = connectionDtos;
    }
}
