package com.openclassrooms.paymybuddy.service.impl;

import com.openclassrooms.paymybuddy.model.Account;
import com.openclassrooms.paymybuddy.repository.IAccountRepository;
import com.openclassrooms.paymybuddy.service.IAccountService;
import com.openclassrooms.paymybuddy.service.dto.AccountDTO;
import com.openclassrooms.paymybuddy.service.mapper.IAccountMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements IAccountService {

    private final IAccountRepository repository;

    public AccountServiceImpl(IAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public AccountDTO createAnAccount(AccountDTO accountDTO) {
        Account account = this.repository.save(IAccountMapper.INSTANCE.accountDtoToAccount(accountDTO));
        return IAccountMapper.INSTANCE.accountToAccountDto(account);
    }

    @Override
    public AccountDTO updateAccount(Float balance, Integer accountId) {
        Optional<Account> optionalAccount = this.repository.findById(accountId);
        Account account = new Account();
        if (optionalAccount.isPresent()) {
            account = optionalAccount.get();
        }
        account = this.repository.updateBalanceOfAccount(balance, account);
        return IAccountMapper.INSTANCE.accountToAccountDto(account);
    }
}
