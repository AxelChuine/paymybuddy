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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
        this.account = new Account(1, null, null, null, null, null, 20.0F, null);
        this.accountDTO = new AccountDTO(1, null, null, null, null, null, 20.0F, null);
        this.transactionsDtos = Set.of(new TransactionDTO(1, "test", 20.00F, 1, 2, LocalDateTime.now()));
    }

    @Test
    public void createAnAccountShouldCreateAnAccount() {
        AccountDTO account = new AccountDTO();

        Mockito.when(this.repository.save(this.account)).thenReturn(this.account);
        account = this.service.createAnAccount(this.accountDTO);

        assertEquals(this.accountDTO, account);
    }

    @Test
    public void findAllAccountShouldReturnAllAccounts() {
        List<Account> accounts = List.of(new Account(), new Account());
        List<AccountDTO> accountDTOS = List.of(new AccountDTO(), new AccountDTO());

        Mockito.when(this.repository.findAll()).thenReturn(accounts);
        List<AccountDTO> accountDtosToCompare = this.service.findAll();

        assertEquals(accountDTOS, accountDtosToCompare);
    }

    @Test
    public void findAccountByEmailAndPasswordShouldReturnAnAccountIfExists() {
        String email = "test@test.com";
        String password = "test";

        Mockito.when(this.repository.findByEmailAndPassword(email, password)).thenReturn(this.account);
        AccountDTO accountToCompare = this.service.findAccountByEmailAndPassword(email, password);

        assertEquals(this.accountDTO, accountToCompare);
    }

    @Test
    public void findAccountByEmailAndPasswordShouldReturnNullIfNotExists() {
        String email = "test@test.com";
        String password = "test";

        Mockito.when(this.repository.findByEmailAndPassword(email, password)).thenReturn(null);
        AccountDTO accountToCompare = this.service.findAccountByEmailAndPassword(email, password);

        assertNull(accountToCompare);
    }

    @Test
    public void findAllConnectionsShouldReturnAListOfConnections() {
        List<Account> accounts = List.of(new Account(), new Account());
        List<AccountDTO> accountDTOS = List.of(new AccountDTO(), new AccountDTO());

        Mockito.when(this.repository.findAllConnectionsByIdentifier(1)).thenReturn(accounts);
        List<AccountDTO> connectionsToCompare = this.service.findAllConnectionsByAccountId(1);

        assertEquals(accountDTOS, connectionsToCompare);
    }

    @Test
    public void findAllConnectionsShouldReturnAnEmptyListIfNoConnectionsExists() {
        List<Account> connections = List.of();
        List<AccountDTO> connectionsDtos = List.of();

        Mockito.when(this.repository.findAllConnectionsByIdentifier(1)).thenReturn(connections);
        List<AccountDTO> connectionsToCompare = this.service.findAllConnectionsByAccountId(1);

        assertEquals(connectionsDtos, connectionsToCompare);
    }

    @Test
    public void findByEmailShouldReturnAnAccount() {
        String email = "test@test.com";

        Mockito.when(this.repository.findByEmail(email)).thenReturn(this.account);
        AccountDTO accountToCompare = this.service.findByEmail(email);

        Assertions.assertEquals(this.accountDTO, accountToCompare);
    }

    @Test
    public void updateBalanceShouldUpdateTheBalanceOfTheAccount() {
        Float amount = 20.0F;

        Mockito.when(this.repository.save(this.account)).thenReturn(this.account);
        AccountDTO accountToCompare = this.service.updateAccount(this.accountDTO, amount);

        assertEquals(this.accountDTO, accountToCompare);
    }

    @Test
    public void findByIdShouldReturnAnAccountIfAccountExists() {
        Integer senderId = 1;

        Mockito.when(this.repository.findById(senderId)).thenReturn(Optional.ofNullable(this.account));
        AccountDTO accountToCompare = this.service.findById(senderId);

        Assertions.assertEquals(this.accountDTO, accountToCompare);
    }

    @Test
    public void savingAnAccountShouldReturnAnAccount() {
        Mockito.when(this.repository.save(this.account)).thenReturn(this.account);
        AccountDTO accountToCompare = this.service.save(this.accountDTO);

        assertEquals(this.accountDTO, accountToCompare);
    }
}
