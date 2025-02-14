package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.dtos.AccountVM;
import com.paymybuddy.paymybuddy.dtos.TransactionDto;
import com.paymybuddy.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.paymybuddy.exceptions.ParameterNotProvidedException;
import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.models.Transaction;
import com.paymybuddy.paymybuddy.repository.ITransactionRepository;
import com.paymybuddy.paymybuddy.services.mapper.AccountMapper;
import com.paymybuddy.paymybuddy.services.mapper.TransactionMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @InjectMocks
    private TransactionService service;

    @Mock
    private ITransactionRepository repository;

    @Mock
    private AccountService accountService;

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private TransactionMapper mapper;

    private AccountVM senderVM;

    private AccountVM receiverVM = new AccountVM();

    private Account sender = new Account();

    private Account receiver = new Account();

    private final Long id = 1L;

    private final String name = "test";

    private BigDecimal amount = new BigDecimal("100.00");

    private final LocalDateTime transactionDate = LocalDateTime.now();

    private Transaction transaction;

    private TransactionDto transactionDto;

    private List<TransactionDto> transactionDtoList;

    private List<Transaction> transactionList;

    @BeforeEach
    public void setUp() {
        this.transactionDto = new TransactionDto(
                this.id,
                this.name,
                this.amount,
                this.senderVM,
                this.receiverVM,
                this.transactionDate
        );
        this.transaction = new Transaction(
                this.id,
                this.name,
                this.amount,
                this.sender,
                this.receiver,
                this.transactionDate
        );
        this.senderVM = new AccountVM(1L);
        this.receiverVM = new AccountVM(2L);
        this.sender = new Account(1L);
        this.receiver = new Account(2L);
        this.transactionDtoList = List.of(this.transactionDto);
        this.transactionList = List.of(this.transaction);
    }

    @Test
    public void findAllByAccountIdShouldReturnAListOfTransactionVM() throws ParameterNotProvidedException, AccountNotFoundException {
        Mockito.when(this.accountService.findAccount(1L)).thenReturn(this.senderVM);
        Mockito.when(this.accountMapper.accountVMToModel(this.senderVM)).thenReturn(this.sender);
        Mockito.when(this.repository.findAllBySender(this.sender)).thenReturn(this.transactionList);
        Mockito.when(this.mapper.toTransactionDtoList(this.transactionList)).thenReturn(this.transactionDtoList);
        List<TransactionDto> transactionDtoListToCompare = this.service.findAllByAccountId(senderVM.getIdentifier());

        Assertions.assertEquals(transactionDtoListToCompare, this.transactionDtoList);
    }
}
