package com.openclassrooms.paymybuddy.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer identifier;

    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private BigDecimal amount;

    @JoinColumn(name = "sender")
    private Integer sender;

    @JoinColumn(name = "receiver")
    private Integer receiver;

    public Transaction(String name, BigDecimal amount, Integer sender, Integer receiver) {
        this.name = name;
        this.amount = amount;
        this.sender = sender;
        this.receiver = receiver;
    }
}
