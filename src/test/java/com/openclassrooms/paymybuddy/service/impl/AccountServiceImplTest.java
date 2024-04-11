package com.openclassrooms.paymybuddy.service.impl;

import com.openclassrooms.paymybuddy.model.Account;
import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.repository.IAccountRepository;
import com.openclassrooms.paymybuddy.service.dto.AccountDTO;
import com.openclassrooms.paymybuddy.service.dto.TransactionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
    public void setAccount () {
        LocalDateTime transactionDate = LocalDateTime.now();
        this.account = new Account(1, "test", "compte personnel", 1, transactions, 50.00F);
        this.accountDTO = new AccountDTO(1, "test", "compte personnel", 1, transactionsDtos, 50.00F);
        this.transactionsDtos = Set.of(new TransactionDTO(1, "test", 20.00F, 2, 1, transactionDate));
    }

    @Test
    public void createAnAccountShouldCreateAnAccount () {
        AccountDTO account = new AccountDTO();

        when(this.repository.save(this.account)).thenReturn(this.account);
        account = this.service.createAnAccount(this.accountDTO);

        assertEquals(this.accountDTO, account);
    }

    @Test
    public void updateAnAccountShouldUpdateTheAccount () {
        Account account = new Account(1, null, null, null, null, 20.0F);
        AccountDTO accountDTO = new AccountDTO(1, null, null, null, null, 20.0F);
        Integer accountId = 1;
        Float balance = 20.0F;

        when(this.repository.findById(accountId)).thenReturn(Optional.of(account));
        when(this.repository.save(account)).thenReturn(account);
        AccountDTO accountDto = this.service.updateAccount(balance, accountId);

        assertEquals(accountDTO, accountDto);
    }

    @Test
    public void sendMoneyShouldAddMoneyAndUpdateBalance() {
        AccountDTO account = new AccountDTO(1, null, null, null, null, 20.0F);
        AccountDTO accountToCompare = new AccountDTO(1, null, null, null, null, 40.0F);
        Float amount = 20.0F;

        AccountDTO accountDTO = this.service.addMoney(account, amount);

        assertEquals(accountToCompare, accountDTO);
    }

    @Test
    public void findAllAccountShouldReturnAllAccounts () {
        List<Account> accounts = List.of(new Account(), new Account());
        List<AccountDTO> accountDTOS = List.of(new AccountDTO(), new AccountDTO());

        when(this.repository.findAll()).thenReturn(accounts);
        List<AccountDTO> accountDtosToCompare = this.service.findAll();

        assertEquals(accountDTOS, accountDtosToCompare);
    }

    @Test
    public void findByIdShouldReturnAccountById () {
        Integer accountId = 1;

        when(this.repository.findById(accountId)).thenReturn(Optional.of(this.account));
        AccountDTO accountDTO = this.service.findById(accountId);

        assertEquals(this.accountDTO, accountDTO);
    }

    @Test
    public void saveNewBalanceShouldReturnAnAccountWithANewBalance () {
        when(this.repository.save(this.account)).thenReturn(this.account);
        AccountDTO accountToCompare = this.service.save(accountDTO);

        assertEquals(this.accountDTO, accountToCompare);
    }
}
