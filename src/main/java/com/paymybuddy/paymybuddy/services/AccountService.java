package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.paymybuddy.exceptions.ParameterNotProvidedException;
import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.repository.IAccountRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AccountService {
    private final IAccountRepository repository;

    @Setter
    @Getter
    private Account account;

    public AccountService(IAccountRepository repository) {
        this.repository = repository;
    }

    public Account findAccount(Long accountId) throws AccountNotFoundException, ParameterNotProvidedException {
        if (Objects.isNull(accountId)) {
            throw new ParameterNotProvidedException();
        }
        Account account = this.repository.findByIdentifier(accountId);
        if (Objects.isNull(account)) {
            throw new AccountNotFoundException();
        }
        return account;
    }

    public Account updateAccount(Account account) throws AccountNotFoundException, ParameterNotProvidedException {
        if (Objects.isNull(account)) {
            throw new ParameterNotProvidedException();
        }
        boolean exists = this.repository.findById(account.getIdentifier()).isPresent();
        if (!exists) {
            throw new AccountNotFoundException();
        }
        return this.repository.save(account);
    }

    public Account findByName(String accountName) throws AccountNotFoundException {
        if (Objects.isNull(accountName)) {
            throw new AccountNotFoundException();
        }
        return this.repository.findByName(accountName);
    }

    public Account save(Account account) throws ParameterNotProvidedException, AccountNotFoundException {
        if (Objects.isNull(account)) {
            throw new ParameterNotProvidedException();
        }
        return this.repository.save(account);
    }


    public Account findByEmail(String accountEmail) throws AccountNotFoundException, ParameterNotProvidedException {
        if (Objects.isNull(accountEmail)) {
            throw new ParameterNotProvidedException();
        }
        Account connection = this.repository.findByEmail(accountEmail);
        if (Objects.isNull(connection)) {
            throw new AccountNotFoundException();
        }
        return connection;
    }

    public Account findById(final long l) {
        Optional<Account> optionalAccount = this.repository.findById(l);
        return optionalAccount.orElse(null);
    }

    public Account findByUsernameAndPassword(String username, String password) throws BadRequestException {
        if (Objects.isNull(username) || Objects.isNull(password)) {
            throw new BadRequestException("Aucun username ou mot de passe renseigné");
        }
        return this.repository.findByEmailAndPassword(username, password);
    }

    public Account createAccount(@NotEmpty(message = "The email is required") @Email String email, String username, String password) {
        Account account = new Account();
        account.setEmail(email);
        account.setUsername(username);
        account.setPassword(password);
        return this.repository.save(account);
    }

}
