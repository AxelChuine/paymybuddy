package com.paymybuddy.paymybuddy.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction", schema = "pay_my_buddy")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "identifier", nullable = false)
    private Long identifier;

    @Column(name = "name")
    private String name;

    @Column(name = "amount", precision = 38, scale = 2)
    private BigDecimal amount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sender", nullable = false)
    private Account sender;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receiver", nullable = false)
    private Account receiver;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    public Transaction(String name, BigDecimal amount, Account sender, Account receiver, LocalDateTime transactionDate) {
        this.name = name;
        this.amount = amount;
        this.sender = sender;
        this.receiver = receiver;
        this.transactionDate = transactionDate;
    }
}