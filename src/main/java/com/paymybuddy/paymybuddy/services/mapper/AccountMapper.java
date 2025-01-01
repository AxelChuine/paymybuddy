package com.paymybuddy.paymybuddy.services.mapper;

import com.paymybuddy.paymybuddy.dtos.AccountDto;
import com.paymybuddy.paymybuddy.models.Account;
import org.springframework.stereotype.Service;

@Service
public class AccountMapper {
    public AccountDto toDto(Account account) {
        return new AccountDto(account.getIdentifier(),
                account.getFirstName(),
                account.getLastName(),
                account.getEmail());
    }
}