package com.openclassrooms.paymybuddy.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


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

    @JoinColumn(name = "transaction_date")
    private LocalDateTime transactionDate;

    public Transaction(String name, BigDecimal amount, Integer sender, Integer receiver, LocalDateTime transactionDate) {
        this.name = name;
        this.amount = amount;
        this.sender = sender;
        this.receiver = receiver;
        this.transactionDate = transactionDate;
    }
}
