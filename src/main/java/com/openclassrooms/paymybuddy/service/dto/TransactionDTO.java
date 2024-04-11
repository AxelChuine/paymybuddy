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
    private Integer sender;
    private Integer receiver;
    private LocalDateTime transactionDate;

    public TransactionDTO(Float amount, Integer sender, Integer receiver, LocalDateTime transactionDate) {
        this.amount = amount;
        this.sender = sender;
        this.receiver = receiver;
        this.transactionDate = transactionDate;
    }

}
