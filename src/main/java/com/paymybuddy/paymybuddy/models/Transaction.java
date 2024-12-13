package com.paymybuddy.paymybuddy.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "transaction", schema = "pay_my_buddy")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "identifier", nullable = false)
    private Long id;

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
    private Instant transactionDate;

}