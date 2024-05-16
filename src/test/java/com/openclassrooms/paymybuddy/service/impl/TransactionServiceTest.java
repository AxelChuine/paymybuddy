package com.openclassrooms.paymybuddy.service.impl;

import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.repository.ITransactionRepository;
import com.openclassrooms.paymybuddy.service.IAccountService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private ITransactionRepository repository;

    @Mock
    private IAccountService accountService;

    @InjectMocks
    private TransactionServiceImpl service;

    private Transaction transaction;

    private TransactionDTO transactionDTO;

    private AccountDTO sender;

    private AccountDTO recipient;


    @BeforeEach
    public void setTransaction () {
        transaction = new Transaction( 50.0F, 1, 2, LocalDateTime.now());
        transactionDTO = new TransactionDTO(50.0F, 1, 2, LocalDateTime.now());
        this.sender = new AccountDTO(1, null, null, null, null, null, 100.0F, null);
        this.recipient = new AccountDTO(1, null, null, null, null, null, 100.0F, null);
    }

    @Test
    public void findAllTransactionsByAccountIdShouldReturnAListOfTransactions () {
        List<Transaction> transactions = List.of(new Transaction(), new Transaction());
        List<TransactionDTO> transactionDtoList = List.of(new TransactionDTO(), new TransactionDTO());
        Integer accountId = 1;

        when(this.repository.findAllBySenderIdOrReceiverId(1, 1)).thenReturn(transactions);
        List<TransactionDTO> transactionDtoToCompare = this.service.findAllByAccountId(accountId);

        assertEquals(transactionDtoList, transactionDtoToCompare);
    }

    @Test
    public void payMyBuddyShouldReturnATransaction () {
        String emailReceiver = "test@test.com";
        Integer identifier = 1;
        AccountDTO senderToSave = new AccountDTO(1, null, null, null, null, null, 50.0F, null);
        AccountDTO recipientToSave = new AccountDTO(1, null, null, null, null, null, 150.0F, null);

        Mockito.when(this.accountService.findById(identifier)).thenReturn(this.sender);
        Mockito.when(this.accountService.findByEmail(emailReceiver)).thenReturn(this.recipient);
        Mockito.when(this.accountService.save(senderToSave)).thenReturn(senderToSave);
        Mockito.when(this.repository.save(Mockito.any(Transaction.class))).thenReturn(transaction);
        TransactionDTO transactionToCompare = this.service.payMyBuddy(this.transaction.getSenderId(), emailReceiver, this.transaction.getAmount());

        Assertions.assertEquals(this.transactionDTO, transactionToCompare);
    }
}
