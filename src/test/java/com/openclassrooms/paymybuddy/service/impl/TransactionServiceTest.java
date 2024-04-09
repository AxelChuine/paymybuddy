package com.openclassrooms.paymybuddy.service.impl;

import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.repository.ITransactionRepository;
import com.openclassrooms.paymybuddy.service.IAccountService;
import com.openclassrooms.paymybuddy.service.dto.AccountDTO;
import com.openclassrooms.paymybuddy.service.dto.TransactionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
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


    @BeforeEach
    public void setTransaction () {
        transaction = new Transaction();
        transactionDTO = new TransactionDTO();
    }

    @Test
    public void findAllTransactionsByAccountIdShouldReturnAListOfTransactions () {
        List<Transaction> transactions = List.of(new Transaction(), new Transaction());
        List<TransactionDTO> transactionDtoList = List.of(new TransactionDTO(), new TransactionDTO());

        when(this.repository.findAllByAccountId(1)).thenReturn(transactions);
        List<TransactionDTO> transactionDtoToCompare = this.service.findByAccountId();

        assertEquals(transactionDtoList, transactionDtoToCompare);
    }

    @Test
    public void sendMoneyToAnotherUserShouldSendMoneyToAnotherUser () {
        Integer senderId = 1;
        Integer receiverId = 2;
        AccountDTO sender = new AccountDTO(senderId, null, null, null, null, 100.0F);
        AccountDTO receiver = new AccountDTO(receiverId, null, null, null, null, null);
        Transaction transaction = new Transaction("restaurant", new BigDecimal("50.0"), 1, 2);

        Mockito.when(this.accountService.findById(receiverId)).thenReturn(receiver);
        when(this.accountService.findById(senderId)).thenReturn(sender);
        /*when(this.repository.save(transaction)).thenReturn(transaction);*/
        doReturn(transaction).when(this.repository.save(transaction));
        when(this.accountService.save(receiver)).thenReturn(receiver);
        /*when(this.accountService.save(sender)).thenReturn(sender);*/
        doReturn(sender).when(this.accountService.save(sender));
        TransactionDTO transactionDTO = this.service.sendMoneyToAFriendById(senderId, receiverId, 50.0F);

        assertEquals(this.transactionDTO, transactionDTO);
    }
}
