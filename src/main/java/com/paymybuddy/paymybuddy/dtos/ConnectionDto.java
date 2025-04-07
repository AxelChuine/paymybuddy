package com.paymybuddy.paymybuddy.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionDto {
    private AccountDto accountDto;
    private AccountDto connectionDto;
}