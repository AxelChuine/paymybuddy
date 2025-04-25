package com.paymybuddy.paymybuddy.controllers;

import com.paymybuddy.paymybuddy.dtos.AccountDto;
import com.paymybuddy.paymybuddy.dtos.ConnectionVM;
import com.paymybuddy.paymybuddy.dtos.TransactionDto;
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
    private final String name = "name";
    private final String nameConnection = "nameConnection";
    private final String firstName = "firstName";
    private final String firstNameConnection = "firstNameConnection";
    private final String lastName = "lastName";
    private final String lastNameConnection = "lastNameConnection";
    private final String username = "username";
    private final String email = "email";
    private final String password = "password";
    private final String nameTransaction = "nameTransaction";

    private AccountDto accountDto;
    private AccountDto connectionAccount;
    private ConnectionVM connectionVM;
    private TransactionDto transactionDto;
    private List<TransactionDto> transactionDtoList = new ArrayList<>();
    private List<AccountDto> accountDtoList = new ArrayList<>();
    private List<ConnectionVM> connectionVMList = new ArrayList<>();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
        this.accountDto = new AccountDto();
        this.accountDto.setIdentifier(accountId);
        this.accountDto.setBalance(new BigDecimal("100.00"));
        this.connectionAccount = new AccountDto();
        this.connectionAccount.setIdentifier(connectionId);
        this.connectionAccount.setBalance(new BigDecimal("100.00"));
        this.transactionDto = new TransactionDto();
        this.transactionDto.setAmount(amount);
        this.transactionDto.setName(nameTransaction);
        this.transactionDto.setRecipient(connectionAccount);
        this.transactionDto.setSender(accountDto);
        this.transactionDtoList.add(transactionDto);
        this.accountDtoList.add(accountDto);
        this.accountDtoList.add(connectionAccount);
        this.connectionVM = new ConnectionVM(
                accountId,
                connectionId,
                firstNameConnection,
                lastNameConnection
        );
        this.connectionVMList.add(this.connectionVM);
    }

    @Test
    public void findAllShouldReturnHttpStatusOk() throws Exception {
        // Mock service calls
        Mockito.when(accountService.getAccountDto()).thenReturn(accountDto);
        Mockito.when(connectionService.findAllByAccount(accountDto)).thenReturn(connectionVMList);
        Mockito.when(accountService.findAccount(this.connectionId)).thenReturn(connectionAccount);
        Mockito.when(service.findAllByAccountId(this.accountId)).thenReturn(transactionDtoList);

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
        Mockito.when(accountService.getAccountDto()).thenReturn(accountDto);
        Mockito.when(service.findAllByAccountId(this.accountId)).thenReturn(transactionDtoList);
        Mockito.when(this.service.create(Mockito.any(TransactionDto.class))).thenReturn(this.transactionDto);

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
        AccountDto accountDto = new AccountDto();
        accountDto.setIdentifier(accountId);
        accountDto.setBalance(new BigDecimal("10.00"));

        Mockito.when(accountService.findById(this.connectionId)).thenReturn(connectionAccount);
        Mockito.when(accountService.getAccountDto()).thenReturn(accountDto);
        Mockito.when(service.findAllByAccountId(this.accountId)).thenReturn(transactionDtoList);
        Mockito.when(this.accountService.getAccountDto()).thenReturn(accountDto);
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
