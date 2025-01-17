package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.dtos.AccountVM;
import com.paymybuddy.paymybuddy.dtos.ConnectionVM;
import com.paymybuddy.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.paymybuddy.exceptions.ParameterNotProvidedException;
import com.paymybuddy.paymybuddy.models.Connection;
import com.paymybuddy.paymybuddy.repository.IConnectionRepository;
import com.paymybuddy.paymybuddy.services.mapper.AccountMapper;
import com.paymybuddy.paymybuddy.services.mapper.ConnectionMapper;
import org.springframework.stereotype.Service;

@Service
public class ConnectionService {
    private final ConnectionMapper mapper;

    private final IConnectionRepository repository;

    private final AccountService accountService;

    private final AccountMapper accountMapper;


    public ConnectionService(ConnectionMapper mapper, IConnectionRepository repository, AccountService accountService, AccountMapper accountMapper) {
        this.mapper = mapper;
        this.repository = repository;
        this.accountService = accountService;
        this.accountMapper = accountMapper;
    }

    public ConnectionVM create(AccountVM accountVM, AccountVM connection) throws ParameterNotProvidedException, AccountNotFoundException {
        AccountVM account = this.accountService.findAccount(accountVM.getIdentifier());
        AccountVM connectionVM = this.accountService.findAccount(connection.getIdentifier());
        Connection connectionToSave = new Connection(
                this.accountMapper.accountVMToModel(account),
                this.accountMapper.accountVMToModel(connectionVM)
        );
        return this.mapper.toVM(this.repository.save(connectionToSave));
    }
}
