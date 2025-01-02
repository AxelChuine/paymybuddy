package com.paymybuddy.paymybuddy.services.mapper;

import com.paymybuddy.paymybuddy.dtos.AccountDto;
import com.paymybuddy.paymybuddy.dtos.AccountVM;
import com.paymybuddy.paymybuddy.models.Account;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class AccountMapper {
    public AccountVM toDto(Account account) {
        return new AccountVM(account.getIdentifier(),
                account.getFirstName(),
                account.getLastName(),
                account.getEmail());
    }

    /*public Account toModel(AccountDto accountDto) {
        return new Account(
                accountDto.getIdentifier(),
                accountDto.getFirstName(),
                accountDto.getLastName(), accountDto.getPassword(),
                accountDto.getPassword(),
                accountDto.getEmail(),
                accountDto.getName(),
                accountDto.getBalance(),
                new HashSet<Account>();
        );*/
    }
}