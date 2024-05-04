package com.openclassrooms.paymybuddy.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
    private Float amount;

    @Column(name = "sender")
    private Integer senderId;

    @Column(name = "receiver")
    private Integer receiverId;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    public Transaction(Float amount, Integer senderId, Integer receiverId, LocalDateTime transactionDate) {
        this.amount = amount;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.transactionDate = transactionDate;
    }
}
