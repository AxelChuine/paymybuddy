package com.openclassrooms.paymybuddy.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private Integer identifier;
    private String name;
    private Float amount;
    private Integer senderId;
    private Integer receiverId;
    private LocalDateTime transactionDate;

    public TransactionDTO(Float amount, Integer senderId, Integer receiverId, LocalDateTime transactionDate) {
        this.amount = amount;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.transactionDate = transactionDate;
    }
}
