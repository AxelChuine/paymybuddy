package com.paymybuddy.paymybuddy.services;

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

    public AccountVM createAccount(Account account) throws AccountAlreadyExistsException, AccountNotFoundException {
      if (this.findAll().stream().anyMatch(a -> Objects.equals(account.getFirstName(), a.getFirstName()) && Objects.equals(account.getLastName(), a.getLastName()))) {
        throw new AccountAlreadyExistsException();
      }
      return this.mapper.toAccountVM(this.repository.save(account));
    }

    public AccountVM findAccount(Long accountId) throws AccountNotFoundException, ParameterNotProvidedException {
        if (Objects.isNull(accountId)) {
            throw new ParameterNotProvidedException();
        }
        Account account = this.repository.findByIdentifier(accountId);
        if (Objects.isNull(account)) {
            throw new AccountNotFoundException();
        }
        return this.mapper.toAccountVM(account);
    }

    public AccountVM updateAccount(AccountVM account) throws AccountNotFoundException, ParameterNotProvidedException {
        if (Objects.isNull(account)) {
            throw new ParameterNotProvidedException();
        }
        Account oldAccount = this.repository.findByIdentifier(account.getIdentifier());
        if (Objects.isNull(oldAccount)) {
            throw new AccountNotFoundException();
        }
        Account accountToTransformBeforeReturn = this.mapper.accountVMToModel(account);
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

    public AccountVM findByName(String accountName) throws AccountNotFoundException {
        if (Objects.isNull(accountName)) {
            throw new AccountNotFoundException();
        }
        return this.mapper.toAccountVM(this.repository.findByName(accountName));
    }

    public AccountVM save(AccountVM account) throws AccountAlreadyExistsException, ParameterNotProvidedException {
        if (Objects.isNull(account)) {
            throw new ParameterNotProvidedException();
        }
        return this.mapper.toAccountVM(this.repository.save(this.mapper.accountVMToModel(account)));
    }

    public AccountVM findByEmail(String accountEmail) throws AccountNotFoundException, ParameterNotProvidedException {
        if (Objects.isNull(accountEmail)) {
            throw new ParameterNotProvidedException();
        }
        AccountVM accountVM = this.mapper.toAccountVM(this.repository.findByEmail(accountEmail));
        if (Objects.isNull(accountVM)) {
            throw new AccountNotFoundException();
        }
        return accountVM;
    }
}
