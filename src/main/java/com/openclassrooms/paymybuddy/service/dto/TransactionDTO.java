package com.openclassrooms.paymybuddy.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private Integer identifier;
    private String name;
    private Float amount;
    private Integer accountId;

}
