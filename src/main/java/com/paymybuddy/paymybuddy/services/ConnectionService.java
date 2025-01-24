package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.dtos.AccountVM;
import com.paymybuddy.paymybuddy.dtos.ConnectionVM;
import com.paymybuddy.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.paymybuddy.exceptions.ConnectionNotFoundException;
import com.paymybuddy.paymybuddy.exceptions.ParameterNotProvidedException;
import com.paymybuddy.paymybuddy.models.Connection;
import com.paymybuddy.paymybuddy.repository.IConnectionRepository;
import com.paymybuddy.paymybuddy.services.mapper.AccountMapper;
import com.paymybuddy.paymybuddy.services.mapper.ConnectionMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
        if (Objects.isNull(accountVM) || Objects.isNull(connection)) {
            throw new ParameterNotProvidedException();
        }
        AccountVM account = this.accountService.findAccount(accountVM.getIdentifier());
        AccountVM connectionVM = this.accountService.findAccount(connection.getIdentifier());
        if (Objects.isNull(account) || Objects.isNull(connectionVM)) {
            throw new AccountNotFoundException();
        }
        Connection connectionToSave = new Connection(
                this.accountMapper.accountVMToModel(account),
                this.accountMapper.accountVMToModel(connectionVM)
        );
        Connection ConnectionWithSender = new Connection(this.accountMapper.accountVMToModel(connectionVM), this.accountMapper.accountVMToModel(accountVM));
        this.repository.save(ConnectionWithSender);
        return this.mapper.toVM(this.repository.save(connectionToSave));
    }

    public ConnectionVM create(AccountVM accountVM, String email) throws ParameterNotProvidedException, AccountNotFoundException {
        if (Objects.isNull(accountVM) || Objects.isNull(email)) {
            throw new ParameterNotProvidedException();
        }
        AccountVM account = this.accountService.findAccount(accountVM.getIdentifier());
        AccountVM connectionVM = this.accountService.findByEmail(email);
        if (Objects.isNull(account) || Objects.isNull(connectionVM)) {
            throw new AccountNotFoundException();
        }
        if (this.repository.findAllByAccount(this.accountMapper.accountVMToModel(account)).stream().anyMatch(a -> Objects.equals(a.getConnection().getEmail()), email)) {

        }
        return new ConnectionVM(account.getIdentifier(), connectionVM.getIdentifier());
    }

    public List<ConnectionVM> findAllByAccount(AccountVM account) throws ParameterNotProvidedException, ConnectionNotFoundException {
        if (Objects.isNull(account)) {
            throw new ParameterNotProvidedException();
        }
        List<ConnectionVM> connectionVMList = this.mapper.toVMList(this.repository.findAllByAccount(this.accountMapper.accountVMToModel(account)));
        if (Objects.isNull(connectionVMList)) {
            throw new ConnectionNotFoundException();
        }
        return connectionVMList;
    }

    public void delete(Long accountId, Long connectionId) throws ParameterNotProvidedException, AccountNotFoundException {
        if (Objects.isNull(accountId) || Objects.isNull(connectionId)) {
            throw new ParameterNotProvidedException();
        }
        this.repository.delete(this.repository.findByConnection(this.accountMapper.accountVMToModel(this.accountService.findAccount(connectionId))));
    }
}
