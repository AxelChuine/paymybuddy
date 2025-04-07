package com.paymybuddy.paymybuddy.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long identifier;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username")
    private String username;

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
}
