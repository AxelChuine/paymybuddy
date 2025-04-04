package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.dtos.AccountDto;
import com.paymybuddy.paymybuddy.dtos.ConnectionDto;
import com.paymybuddy.paymybuddy.dtos.ConnectionVM;
import com.paymybuddy.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.paymybuddy.exceptions.ConnectionNotFoundException;
import com.paymybuddy.paymybuddy.exceptions.ParameterNotProvidedException;
import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.models.Connection;
import com.paymybuddy.paymybuddy.repository.IAccountRepository;
import com.paymybuddy.paymybuddy.repository.IConnectionRepository;
import com.paymybuddy.paymybuddy.services.mapper.AccountMapper;
import com.paymybuddy.paymybuddy.services.mapper.ConnectionMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ConnectionService {
    private final ConnectionMapper mapper;

    private final IConnectionRepository repository;

    private final IAccountRepository accountRepository;

    private final AccountService accountService;

    private final AccountMapper accountMapper;


    public ConnectionService(ConnectionMapper mapper, IConnectionRepository repository, IAccountRepository accountRepository, AccountService accountService, AccountMapper accountMapper) {
        this.mapper = mapper;
        this.repository = repository;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.accountMapper = accountMapper;
    }

    public ConnectionDto create(AccountDto accountDto, AccountDto connection) throws ParameterNotProvidedException, AccountNotFoundException {
        if (Objects.isNull(accountDto) || Objects.isNull(connection)) {
            throw new ParameterNotProvidedException();
        }
        Connection connectionToSave = new Connection(
                this.accountMapper.toModel(accountDto),
                this.accountMapper.toModel(connection)
        );
        return this.mapper.toDto(this.repository.save(connectionToSave));
    }

    public List<ConnectionVM> findAllByAccount(AccountDto account) throws ParameterNotProvidedException, ConnectionNotFoundException {
        if (Objects.isNull(account)) {
            throw new ParameterNotProvidedException();
        }
        Optional<Account> optionalAccount = this.accountRepository.findById(account.getIdentifier());
        List<ConnectionVM> connectionVMList = new ArrayList<>();
        if (optionalAccount.isPresent()) {
            connectionVMList = this.mapper.toVMList(this.repository.findAllByAccount(optionalAccount.get()));
        }
        if (connectionVMList.isEmpty()) {
            throw new ConnectionNotFoundException();
        }
        return connectionVMList;
    }

    public ConnectionDto create(long l, String email) throws ParameterNotProvidedException, AccountNotFoundException {
        AccountDto account = this.accountService.findById(l);
        Optional<Account> optionalConnection = this.accountService.findByEmail(email);
        if (optionalConnection.isPresent()) {
            AccountDto connection = this.accountMapper.toAccountDto(optionalConnection.get());
            return this.create(account, connection);
        }
        return null;
    }
}
