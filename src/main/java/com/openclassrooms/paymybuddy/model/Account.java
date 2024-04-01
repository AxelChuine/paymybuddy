package com.openclassrooms.paymybuddy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer identifier;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @JoinColumn(name = "person_id")
    private Integer personId;

    @OneToMany
    @JoinColumn(name = "transaction_id")
    private Set<Transaction> transactions;

    @Column(name = "balance")
    private Float balance;
}
