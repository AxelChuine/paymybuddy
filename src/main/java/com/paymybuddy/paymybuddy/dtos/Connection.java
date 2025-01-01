package com.paymybuddy.paymybuddy.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Connection {
    private AccountDto accountDto;
    private AccountDto connectionDto;
}