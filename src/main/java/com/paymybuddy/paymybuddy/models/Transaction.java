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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sender", nullable = false)
    private Account sender;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "receiver", nullable = false)
    private Account receiver;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    public Transaction(BigDecimal amount, String name, Account sender, Account receiver, LocalDateTime now) {
        this.amount = amount;
        this.name = name;
        this.sender = sender;
        this.receiver = receiver;
        this.transactionDate = now;
    }
}