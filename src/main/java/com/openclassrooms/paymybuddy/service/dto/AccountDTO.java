package com.openclassrooms.paymybuddy.service.dto;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class AccountDTO {
    private Integer identifier;
    private String name;
    private String description;
    private Integer personId;
    private Set<TransactionDTO> transactionDTOS;
    private Float balance;

}

