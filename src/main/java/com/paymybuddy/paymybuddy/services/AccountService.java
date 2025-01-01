package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.repository.IAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AccountService {
    private final IAccountRepository repository;

    public AccountService(IAccountRepository repository) {
        this.repository = repository;
    }

    public List<Account> findAll() {
        List<Account> accounts = repository.findAll();
        if (Objects.isNull(accounts)) {
            throw new Exception()
        }
    }
}
