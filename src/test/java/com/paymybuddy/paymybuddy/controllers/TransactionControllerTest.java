package com.paymybuddy.paymybuddy.controllers;

import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.models.Connection;
import com.paymybuddy.paymybuddy.models.Transaction;
import com.paymybuddy.paymybuddy.services.AccountService;
import com.paymybuddy.paymybuddy.services.ConnectionService;
import com.paymybuddy.paymybuddy.services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {
    private MockMvc mockMvc;

    @Mock
    private TransactionService service;

    @Mock
    private ConnectionService connectionService;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransactionController transactionController;

    private final Long accountId = 1L;
    private final Long connectionId = 2L;
    private final BigDecimal amount = new BigDecimal("50.00");
    private final String username = "username";
    private final String nameTransaction = "nameTransaction";

    private Account account;
    private Account connectionAccount;
    private Connection connection;
    private Transaction transaction;
    private List<Transaction> transactionList = new ArrayList<>();
    private List<Account> accountList = new ArrayList<>();
    private List<Connection> connectionList = new ArrayList<>();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
        this.account = new Account();
        this.account.setIdentifier(accountId);
        this.account.setBalance(new BigDecimal("100.00"));
        this.connectionAccount = new Account();
        this.connectionAccount.setIdentifier(connectionId);
        this.connectionAccount.setBalance(new BigDecimal("100.00"));
        this.transaction = new Transaction();
        this.transaction.setAmount(amount);
        this.transaction.setName(nameTransaction);
        this.transaction.setRecipient(connectionAccount);
        this.transaction.setSender(account);
        this.transactionList.add(transaction);
        this.accountList.add(account);
        this.accountList.add(connectionAccount);
        this.connection = new Connection(
                this.account,
                this.connectionAccount
        );
        this.connectionList.add(this.connection);
    }

    @Test
    public void findAllShouldReturnHttpStatusOk() throws Exception {
        // Mock service calls
        Mockito.when(accountService.getAccount()).thenReturn(account);
        Mockito.when(connectionService.findAllByAccount(account)).thenReturn(connectionList);
        Mockito.when(accountService.findAccount(this.connectionId)).thenReturn(connectionAccount);
        Mockito.when(service.findAllByAccountId(this.accountId)).thenReturn(transactionList);

        // Act & Assert
        this.mockMvc.perform(MockMvcRequestBuilders.get("/transaction/transaction"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().attributeExists("transaction"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("accountDtoList"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("transactions"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("currentPage"))
            .andExpect(MockMvcResultMatchers.view().name("transaction/transaction"));
    }

    @Test
    public void createTransactionShouldReturnHttpStatusOk() throws Exception {
        Mockito.when(accountService.findById(this.connectionId)).thenReturn(connectionAccount);
        Mockito.when(accountService.getAccount()).thenReturn(account);
        Mockito.when(service.findAllByAccountId(this.accountId)).thenReturn(transactionList);
        Mockito.when(this.service.create(Mockito.any(Transaction.class))).thenReturn(this.transaction);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/transaction/transaction")
                .param("amount", amount.toString())
                .param("name", nameTransaction)
                .param("recipient.identifier", connectionId.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("transaction"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("transactions"))
                .andExpect(MockMvcResultMatchers.view().name("transaction/transaction"));
    }

    @Test
    public void createTransactionShouldReturnHttpStatusOkEvenWhenBalanceIsInferiorToAmount() throws Exception {
        Account newAccount = new Account();
        newAccount.setIdentifier(accountId);
        newAccount.setBalance(new BigDecimal("10.00"));

        Mockito.when(accountService.findById(this.connectionId)).thenReturn(connectionAccount);
        Mockito.when(accountService.getAccount()).thenReturn(newAccount);
        Mockito.when(service.findAllByAccountId(this.accountId)).thenReturn(transactionList);
        Mockito.when(this.accountService.getAccount()).thenReturn(newAccount);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/transaction/transaction")
                .param("amount", amount.toString())
                .param("name", nameTransaction)
                .param("recipient.identifier", connectionId.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("transaction"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("error"))
                .andExpect(MockMvcResultMatchers.view().name("transaction/transaction"));
    }
}
