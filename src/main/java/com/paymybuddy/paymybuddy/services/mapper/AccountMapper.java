package com.paymybuddy.paymybuddy.services.mapper;

import com.paymybuddy.paymybuddy.dtos.AccountDto;
import com.paymybuddy.paymybuddy.dtos.AccountVM;
import com.paymybuddy.paymybuddy.models.Account;
import org.springframework.stereotype.Service;

@Service
public class AccountMapper {
    public AccountVM toAccountVM(Account account) {
        return new AccountVM(account.getIdentifier(),
                account.getFirstName(),
                account.getLastName(),
                account.getEmail(),
                account.getName(),
                account.getBalance());
    }

    public Account toModel(AccountDto accountDto) {
        Account account = new Account();
        account.setIdentifier(accountDto.getIdentifier());
        account.setFirstName(accountDto.getFirstName());
        account.setLastName(accountDto.getLastName());
        account.setEmail(accountDto.getEmail());
        account.setPassword(accountDto.getPassword());
        account.setName(accountDto.getName());
        account.setBalance(accountDto.getBalance());
        return account;
    }

    public Account accountVMToModel(AccountVM accountVM) {
        Account account = new Account();
        account.setIdentifier(accountVM.getIdentifier());
        account.setFirstName(accountVM.getFirstName());
        account.setLastName(accountVM.getLastName());
        account.setEmail(accountVM.getEmail());
        account.setBalance(accountVM.getBalance());
        return account;
    }
}