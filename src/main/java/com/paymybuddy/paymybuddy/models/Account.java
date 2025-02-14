package com.paymybuddy.paymybuddy.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "account")
@EqualsAndHashCode
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long identifier;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "balance")
    private BigDecimal balance;

    
    @ManyToMany
    @JoinTable(
            name = "connection",
            joinColumns = @JoinColumn(name = "account"),
            inverseJoinColumns = @JoinColumn(name = "connection")
    )
    private Set<Account> connections;

    public Account() {
        this.firstName = "";
        this.lastName = "";
        this.password = "";
        this.email = "";
        this.name = "";
        this.balance = BigDecimal.ZERO;
        this.connections = new HashSet<>();
    }

    public Account(Long identifier) {
        this.identifier = identifier;
        this.firstName = "";
        this.lastName = "";
        this.password = "";
        this.email = "";
        this.name = "";
        this.balance = BigDecimal.ZERO;
        this.connections = new HashSet<>();
    }

    public Account(Long identifier, String firstName, String lastName, String password, String email, String name, BigDecimal balance) {
        this.identifier = identifier;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.name = name;
        this.balance = balance;
        this.connections = new HashSet<>();
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

    public Set<Account> getConnections() {
        return connections;
    }

    public void setConnections(Set<Account> connections) {
        this.connections = connections;
    }
}
