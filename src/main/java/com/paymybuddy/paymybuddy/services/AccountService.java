package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.dtos.AccountDto;
import com.paymybuddy.paymybuddy.dtos.AccountVM;
import com.paymybuddy.paymybuddy.exceptions.AccountAlreadyExistsException;
import com.paymybuddy.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.paymybuddy.exceptions.ParameterNotProvidedException;
import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.repository.IAccountRepository;
import com.paymybuddy.paymybuddy.services.mapper.AccountMapper;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountService {
    private final IAccountRepository repository;

    private final AccountMapper mapper;

    @Setter
    @Getter
    private AccountDto accountDto;

    public AccountService(IAccountRepository repository, AccountMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<Account> findAll() throws AccountNotFoundException {
        return repository.findAll();
    }

    public List<AccountDto> findAllDto() throws AccountNotFoundException {
        return this.mapper.toDtoList(this.repository.findAll());
    }

    public AccountDto findAccount(Long accountId) throws AccountNotFoundException, ParameterNotProvidedException {
        if (Objects.isNull(accountId)) {
            throw new ParameterNotProvidedException();
        }
        Account account = this.repository.findByIdentifier(accountId);
        if (Objects.isNull(account)) {
            throw new AccountNotFoundException();
        }
        return this.mapper.toAccountDto(account);
    }

    public AccountVM updateAccount(AccountDto account) throws AccountNotFoundException, ParameterNotProvidedException {
        if (Objects.isNull(account)) {
            throw new ParameterNotProvidedException();
        }
        Account oldAccount = this.repository.findByIdentifier(account.getIdentifier());
        if (Objects.isNull(oldAccount)) {
            throw new AccountNotFoundException();
        }
        Account accountToTransformBeforeReturn = this.repository.findByIdentifier(account.getIdentifier());
        accountToTransformBeforeReturn.setConnections(oldAccount.getConnections());
        return this.mapper.toAccountVM(this.repository.save(accountToTransformBeforeReturn));
    }

    public void deleteAccount(Long accountId) throws AccountNotFoundException, ParameterNotProvidedException {
        if (Objects.isNull(accountId)) {
            throw new ParameterNotProvidedException();
        }
        if (Objects.isNull(this.repository.findByIdentifier(accountId))) {
            throw new AccountNotFoundException();
        }
        this.repository.deleteById(accountId);
    }

    public AccountDto findByName(String accountName) throws AccountNotFoundException {
        if (Objects.isNull(accountName)) {
            throw new AccountNotFoundException();
        }
        return this.mapper.toAccountDto(this.repository.findByName(accountName));
    }

    public AccountDto save(AccountDto account) throws AccountAlreadyExistsException, ParameterNotProvidedException, AccountNotFoundException {
        if (Objects.isNull(account)) {
            throw new ParameterNotProvidedException();
        }
        return this.mapper.toAccountDto(this.repository.save(this.mapper.toModel(account)));
    }


    public Optional<Account> findByEmail(String accountEmail) throws AccountNotFoundException, ParameterNotProvidedException {
        if (Objects.isNull(accountEmail)) {
            throw new ParameterNotProvidedException();
        }
        Optional<Account> optionalAccount = this.repository.findByEmail(accountEmail);
        if (optionalAccount.isEmpty()) {
            throw new AccountNotFoundException();
        }
        return optionalAccount;
    }

    public AccountDto findById(final long l) {
        Optional<Account> optionalAccount = this.repository.findById(l);
        return optionalAccount.map(this.mapper::toAccountDto).orElse(null);
    }

    public AccountDto findByUsernameAndPassword(String username, String password) throws BadRequestException {
        if (Objects.isNull(username) || Objects.isNull(password)) {
            throw new BadRequestException("Aucun username ou mot de passe renseigné");
        }
        Optional<Account> optionalAccount = this.repository.findByEmailAndPassword(username, password);
        if (optionalAccount.isPresent()) {
            return this.mapper.toAccountDto(optionalAccount.get());
        } else {
            throw new BadRequestException("Il semble que le compte ne soit pas créé. Veuillez créer le compte");
        }
    }

    public AccountDto createAccount(@NotEmpty(message = "The email is required") @Email String email, String username, String password) {
        Account account = new Account();
        account.setEmail(email);
        account.setUsername(username);
        account.setPassword(password);
        return this.mapper.toAccountDto(this.repository.save(account));
    }
}
