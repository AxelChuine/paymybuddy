package com.paymybuddy.paymybuddy.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionVM {
    private Long accountId;
    private Long connectionId;
    private String firstNameConnection;
    private String lastNameConnection;
}
