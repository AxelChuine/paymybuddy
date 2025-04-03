package com.paymybuddy.paymybuddy.services.mapper;

import com.paymybuddy.paymybuddy.dtos.AccountDto;
import com.paymybuddy.paymybuddy.dtos.AccountVM;
import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.repository.IAccountRepository;
import org.springframework.stereotype.Service;

import java.util.*;

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
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setBalance(Objects.requireNonNull(accountDto.getBalance()));
            account.setConnections(Objects.nonNull(accountDto.getConnectionDtos()) ? this.toConnectionModelSet(accountDto.getConnectionDtos()) : null);
        }
        return optionalAccount.orElse(null);
    }

    private Set<Account> toConnectionModelSet(Set<AccountDto> connectionDtos) {
        Set<Account> accounts = new HashSet<>();
        for (AccountDto connectionDto : connectionDtos) {
            accounts.add(this.toConnectionModel(connectionDto));
        }
        return accounts;
    }

    private Account toConnectionModel(AccountDto connectionDto) {
        Account account = new Account();
        account.setIdentifier(connectionDto.getIdentifier());
        account.setFirstName(connectionDto.getFirstName());
        account.setLastName(connectionDto.getLastName());
        account.setEmail(connectionDto.getEmail());
        account.setName(connectionDto.getName());
        account.setBalance(connectionDto.getBalance());
        Optional<Account> optional = this.repository.findById(account.getIdentifier());
        optional.ifPresent(value -> account.setPassword(value.getPassword()));
        return account;
    }

    public Account accountVMToModel(AccountDto accountVM) {
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
                account.getUsername(),
                account.getPassword(),
                account.getEmail(),
                account.getName(),
                account.getBalance(),
                null
        );
    }

    public List<AccountDto> toDtoList(List<Account> accountList) {
        List<AccountDto> list = new ArrayList<>();
        for (Account account : accountList) {
            list.add(toAccountDto(account));
        }
        return list;
    }

    public Optional<AccountDto> toOptionalAccountDto(Optional<Account> optionalAccount) {
        return null;
    }
}