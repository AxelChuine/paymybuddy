package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.dtos.AccountDto;
import com.paymybuddy.paymybuddy.dtos.AccountVM;
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

    public ConnectionVM create(AccountDto accountDto, AccountDto connection) throws ParameterNotProvidedException, AccountNotFoundException {
        if (Objects.isNull(accountDto) || Objects.isNull(connection)) {
            throw new ParameterNotProvidedException();
        }
        AccountVM account = this.accountService.findById(accountDto.getIdentifier());
        AccountVM connectionVM = this.accountService.findById(connection.getIdentifier());
        if (Objects.isNull(account) || Objects.isNull(connectionVM)) {
            throw new AccountNotFoundException();
        }
        Connection connectionToSave = new Connection(
                this.accountMapper.accountVMToModel(account),
                this.accountMapper.accountVMToModel(connectionVM)
        );
        Connection ConnectionWithSender = new Connection(this.accountMapper.accountVMToModel(connectionVM), this.accountMapper.toModel(accountDto));
        this.repository.save(ConnectionWithSender);
        return this.mapper.toVM(this.repository.save(connectionToSave));
    }


    //FIXME: tester si la connexion existe
    public ConnectionVM create(AccountVM accountVM, String email) throws ParameterNotProvidedException, AccountNotFoundException {
        if (Objects.isNull(accountVM) || Objects.isNull(email)) {
            throw new ParameterNotProvidedException();
        }
        AccountVM account = this.accountService.findById(accountVM.getIdentifier());
        AccountVM connectionVM = this.accountService.findByEmail(email);
        if (Objects.isNull(account) || Objects.isNull(connectionVM)) {
            throw new AccountNotFoundException();
        }
        return new ConnectionVM(account.getIdentifier(), connectionVM.getIdentifier(), connectionVM.getFirstName(), connectionVM.getLastName());
    }

    public List<ConnectionVM> findAllByAccount(AccountVM account) throws ParameterNotProvidedException, ConnectionNotFoundException {
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

    public void delete(Long accountId, Long connectionId) throws ParameterNotProvidedException, AccountNotFoundException {
        if (Objects.isNull(accountId) || Objects.isNull(connectionId)) {
            throw new ParameterNotProvidedException();
        }
        this.repository.delete(this.repository.findByConnection(this.accountMapper.accountVMToModel(this.accountService.findById(connectionId))));
    }
}
