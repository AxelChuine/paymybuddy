package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.dtos.AccountVM;
import com.paymybuddy.paymybuddy.exceptions.AccountAlreadyExistsException;
import com.paymybuddy.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.repository.IAccountRepository;
import com.paymybuddy.paymybuddy.services.mapper.AccountMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AccountService {
    private final IAccountRepository repository;

    private final AccountMapper mapper;

    public AccountService(IAccountRepository repository, AccountMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<Account> findAll() throws AccountNotFoundException {
        return repository.findAll();
    }

    public AccountVM createAccount(Account account) throws AccountAlreadyExistsException, AccountNotFoundException {
      if (this.findAll().stream().anyMatch(a -> Objects.equals(account.getFirstName(), a.getFirstName()) && Objects.equals(account.getLastName(), a.getLastName()))) {
        throw new AccountAlreadyExistsException();
      }
      return this.mapper.toDto(this.repository.save(account));
    }

    public AccountVM findAccount(Long accountId) throws AccountNotFoundException {
        if (Objects.isNull(accountId)) {
            throw new AccountNotFoundException();
        }
        Account account = this.repository.findByIdentifier(accountId);
        if (Objects.isNull(account)) {
            throw new AccountNotFoundException();
        }
    }
}
