package com.openclassrooms.paymybuddy.service.impl;

import com.openclassrooms.paymybuddy.model.Account;
import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.repository.IAccountRepository;
import com.openclassrooms.paymybuddy.service.dto.AccountDTO;
import com.openclassrooms.paymybuddy.service.dto.TransactionDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @Mock
    private IAccountRepository repository;

    @InjectMocks
    private AccountServiceImpl service;

    private Account account;

    private AccountDTO accountDTO;

    private Set<TransactionDTO> transactionsDtos;

    private Set<Transaction> transactions;

    @BeforeEach
    public void setAccount() {
        this.account = new Account(1, "test", null, null, null, null, 50.00F, null, transactions);
        this.accountDTO = new AccountDTO(1, "test", null, null, null, null, 50.00F, null, transactionsDtos);
        this.transactionsDtos = Set.of(new TransactionDTO(1, "test", 20.00F, 2));
    }

    @Test
    public void createAnAccountShouldCreateAnAccount() {
        AccountDTO account = new AccountDTO();

        Mockito.when(this.repository.save(this.account)).thenReturn(this.account);
        account = this.service.createAnAccount(this.accountDTO);

        Assertions.assertEquals(this.accountDTO, account);
    }

    @Test
    public void updateAnAccountShouldUpdateTheAccount() {
        Account account = new Account(1, null, null, null, null, null, 20.0F, null, null);
        AccountDTO accountDTO = new AccountDTO(1, null, null, null, null, null, 20.0F, null, null);
        Integer accountId = 1;
        Float balance = 20.0F;

        Mockito.when(this.repository.findById(accountId)).thenReturn(Optional.of(account));
        Mockito.when(this.repository.save(account)).thenReturn(account);
        AccountDTO accountDto = this.service.updateAccount(balance, accountId);

        Assertions.assertEquals(accountDTO, accountDto);
    }

    @Test
    public void sendMoneyShouldSendMoneyAndUpdateBalance() {
        Float amount = 20.0F;

        AccountDTO accountToCompare = this.service.sendMoney(this.accountDTO, amount);

        Assertions.assertEquals(this.accountDTO, accountToCompare);
    }

    @Test
    public void findAllAccountShouldReturnAllAccounts() {
        List<Account> accounts = List.of(new Account(), new Account());
        List<AccountDTO> accountDTOS = List.of(new AccountDTO(), new AccountDTO());

        Mockito.when(this.repository.findAll()).thenReturn(accounts);
        List<AccountDTO> accountDtosToCompare = this.service.findAll();

        Assertions.assertEquals(accountDTOS, accountDtosToCompare);
    }
}
