package com.paymybuddy.paymybuddy.services.mapper;

import com.paymybuddy.paymybuddy.dtos.AccountDto;
import com.paymybuddy.paymybuddy.dtos.AccountVM;
import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.repository.IAccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountMapper {

    private final IAccountRepository repository;

    public AccountMapper(IAccountRepository repository) {
        this.repository = repository;
    }

    public AccountVM toAccountVM(Account account) {
        return new AccountVM(account.getIdentifier(),
                account.getFirstName(),
                account.getLastName(),
                account.getEmail(),
                account.getName(),
                account.getBalance());
    }

    public Account toModel(AccountDto accountDto) {
        Optional<Account> optionalAccount = this.repository.findById(accountDto.getIdentifier());
        return optionalAccount.orElse(null);
    }

    public Account accountVMToModel(AccountVM accountVM) {
        Optional<Account> optionalAccount = this.repository.findById(accountVM.getIdentifier());
        if (optionalAccount.isPresent()) {
            return optionalAccount.get();
        }
        return null;
    }

    public AccountDto toAccountDto(Account account) {
        return new AccountDto(
                account.getIdentifier(),
                account.getFirstName(),
                account.getLastName(),
                account.getPassword(),
                account.getEmail(),
                account.getName(),
                account.getBalance(),
                null
        );
    }
}