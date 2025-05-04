package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.paymybuddy.exceptions.ParameterNotProvidedException;
import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.models.Connection;
import com.paymybuddy.paymybuddy.repository.IConnectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ConnectionService {
    private final IConnectionRepository repository;

    private final AccountService accountService;

    public ConnectionService(IConnectionRepository repository, AccountService accountService) {
        this.repository = repository;
        this.accountService = accountService;
    }

    public Connection create(Account account, Account connection) throws ParameterNotProvidedException, AccountNotFoundException {
        if (Objects.isNull(account) || Objects.isNull(connection)) {
            throw new ParameterNotProvidedException();
        }
        Connection connectionToSave = new Connection(
                account,
                connection
        );
        return this.repository.save(connectionToSave);
    }

    public List<Connection> findAllByAccount(Account account) throws ParameterNotProvidedException {
        if (Objects.isNull(account)) {
            throw new ParameterNotProvidedException();
        }
        return this.repository.findAllByAccount(account);
    }

    public Connection create(long l, String email) throws ParameterNotProvidedException, AccountNotFoundException {
        Account account = this.accountService.findById(l);
        Optional<Account> optionalConnection = this.accountService.findByEmail(email);
        if (optionalConnection.isPresent()) {
            Account connection = optionalConnection.get();
            return this.create(account, connection);
        }
        return null;
    }
}
