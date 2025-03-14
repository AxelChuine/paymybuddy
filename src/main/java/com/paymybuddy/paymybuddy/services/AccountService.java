package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.dtos.AccountDto;
import com.paymybuddy.paymybuddy.dtos.AccountVM;
import com.paymybuddy.paymybuddy.exceptions.AccountAlreadyExistsException;
import com.paymybuddy.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.paymybuddy.exceptions.ParameterNotProvidedException;
import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.repository.IAccountRepository;
import com.paymybuddy.paymybuddy.services.mapper.AccountMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public List<AccountDto> findAllDto() throws AccountNotFoundException {
        return this.mapper.toDtoList(this.repository.findAll());
    }

    public AccountVM createAccount(Account account) throws AccountAlreadyExistsException, AccountNotFoundException {
      if (this.findAll().stream().anyMatch(a -> Objects.equals(account.getFirstName(), a.getFirstName()) && Objects.equals(account.getLastName(), a.getLastName()))) {
        throw new AccountAlreadyExistsException();
      }
      return this.mapper.toAccountVM(this.repository.save(account));
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
        Account accountToUpdate = this.repository.findByIdentifier(account.getIdentifier());
        Account accountToSave = new Account();
        accountToSave.setIdentifier(accountToUpdate.getIdentifier());
        accountToSave.setFirstName(accountToUpdate.getFirstName());
        accountToSave.setLastName(accountToUpdate.getLastName());
        accountToSave.setUsername(Objects.nonNull(account.getUsername()) ? account.getUsername() : accountToUpdate.getUsername());
        accountToSave.setPassword(Objects.nonNull(account.getPassword()) ? account.getPassword() : accountToUpdate.getPassword());
        accountToSave.setEmail(Objects.nonNull(account.getEmail()) ? account.getEmail() : accountToUpdate.getEmail());
        accountToSave.setName(accountToUpdate.getName());
        accountToSave.setBalance(accountToUpdate.getBalance());
        accountToSave.setConnections(accountToUpdate.getConnections());

        return this.mapper.toAccountDto(this.repository.save(accountToSave));
    }

    public AccountDto findByEmail(String accountEmail) throws AccountNotFoundException, ParameterNotProvidedException {
        if (Objects.isNull(accountEmail)) {
            throw new ParameterNotProvidedException();
        }
        AccountDto accountDto = this.mapper.toAccountDto(this.repository.findByEmail(accountEmail));
        if (Objects.isNull(accountDto)) {
            throw new AccountNotFoundException();
        }
        return accountDto;
    }

    public AccountDto findById(final long l) {
        Optional<Account> optionalAccount = this.repository.findById(l);
        return optionalAccount.map(this.mapper::toAccountDto).orElse(null);
    }
}
