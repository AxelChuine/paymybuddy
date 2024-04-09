package com.openclassrooms.paymybuddy.service.impl;

import com.openclassrooms.paymybuddy.model.Account;
import com.openclassrooms.paymybuddy.repository.IAccountRepository;
import com.openclassrooms.paymybuddy.service.IAccountService;
import com.openclassrooms.paymybuddy.service.dto.AccountDTO;
import com.openclassrooms.paymybuddy.service.mapper.IAccountMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
        Account account = null;
        if (optionalAccount.isPresent()) {
            account = optionalAccount.get();
            account.setBalance(balance);
        }
        if (Objects.nonNull(account)) {
            account = this.repository.save(account);
        }
        return IAccountMapper.INSTANCE.accountToAccountDto(account);
    }

    @Override
    public AccountDTO addMoney(AccountDTO account, Float amount) {
        AccountDTO accountDTO = account;
        Float balance = accountDTO.getBalance() + amount;
        accountDTO.setBalance(balance);
        this.repository.save(IAccountMapper.INSTANCE.accountDtoToAccount(accountDTO));
        return accountDTO;
    }

    @Override
    public List<AccountDTO> findAll() {
        List<AccountDTO> accountDTOS = IAccountMapper.INSTANCE.accountsToAccountDTOList(this.repository.findAll());
        return accountDTOS;
    }

    @Override
    public AccountDTO findById(Integer senderId) {
        Optional<Account> optionalAccount = this.repository.findById(senderId);
        Account account = null;
        if (optionalAccount.isPresent()) {
            account = optionalAccount.get();
        }
        return IAccountMapper.INSTANCE.accountToAccountDto(account);
    }

    @Override
    public AccountDTO save(AccountDTO account) {
        return IAccountMapper.INSTANCE.accountToAccountDto(this.repository.save(IAccountMapper.INSTANCE.accountDtoToAccount(account)));
    }
}
