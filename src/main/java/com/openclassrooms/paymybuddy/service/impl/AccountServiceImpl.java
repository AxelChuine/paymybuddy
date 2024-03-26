package com.openclassrooms.paymybuddy.service.impl;

import com.openclassrooms.paymybuddy.repository.IAccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl {

    private final IAccountRepository repository;

    public AccountServiceImpl(IAccountRepository repository) {
        this.repository = repository;
    }
}
