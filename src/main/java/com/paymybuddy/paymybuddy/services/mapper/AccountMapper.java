package com.paymybuddy.paymybuddy.services.mapper;

import com.paymybuddy.paymybuddy.dtos.AccountDto;
import com.paymybuddy.paymybuddy.dtos.AccountVM;
import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.repository.IAccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    public Set<Account> toConnectionModelSet(Set<AccountDto> connectionDtos) {
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
        account.setUsername(connectionDto.getUsername());
        account.setPassword(connectionDto.getPassword());
        account.setEmail(connectionDto.getEmail());
        account.setName(connectionDto.getName());
        account.setBalance(connectionDto.getBalance());
        return account;
    }

    public AccountDto toAccountDto(Account account) {
        AccountDto accountDto = new AccountDto();
        accountDto.setIdentifier(Objects.nonNull(account.getIdentifier()) ? account.getIdentifier() : null);
        accountDto.setFirstName(Objects.nonNull(account.getFirstName()) ? account.getFirstName() : "");
        accountDto.setLastName(Objects.nonNull(account.getLastName()) ? account.getLastName() : "");
        accountDto.setUsername(Objects.nonNull(account.getUsername()) ? account.getUsername() : "");
        accountDto.setEmail(Objects.nonNull(account.getEmail()) ? account.getEmail() : "");
        accountDto.setName(Objects.nonNull(account.getName()) ? account.getName() : "");
        accountDto.setBalance(Objects.nonNull(account.getBalance()) ? account.getBalance() : BigDecimal.ZERO);
        /*accountDto.setConnectionDtos(!account.getConnections().isEmpty() ? this.toConnectionDtoSet(account.getConnections()) : new HashSet<>());*/
        return accountDto;
    }

    private Set<AccountDto> toConnectionDtoSet(Set<Account> connections) {
        Set<AccountDto> connectionDtoSet = new HashSet<>();
        for (Account connection : connections) {
            connectionDtoSet.add(this.toConnectionDto(connection));
        }
        return connectionDtoSet;
    }

    private AccountDto toConnectionDto(Account connection) {
        AccountDto accountDto = new AccountDto();
        accountDto.setIdentifier(connection.getIdentifier());
        accountDto.setFirstName(connection.getFirstName());
        accountDto.setLastName(connection.getLastName());
        accountDto.setEmail(connection.getEmail());
        accountDto.setName(connection.getName());
        accountDto.setBalance(connection.getBalance());
        Optional<Account> optional = this.repository.findById(accountDto.getIdentifier());
        optional.ifPresent(value -> accountDto.setPassword(value.getPassword()));
        return accountDto;
    }

    public List<AccountDto> toDtoList(List<Account> accountList) {
        List<AccountDto> list = new ArrayList<>();
        for (Account account : accountList) {
            list.add(toAccountDto(account));
        }
        return list;
    }
}