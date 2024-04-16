package com.openclassrooms.paymybuddy.service.impl;

import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.repository.ITransactionRepository;
import com.openclassrooms.paymybuddy.service.dto.TransactionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private ITransactionRepository repository;

    @InjectMocks
    private TransactionServiceImpl service;


    @BeforeEach
    public void setTransaction () {
        Transaction transaction = new Transaction();
    }

    @Test
    public void findAllTransactionsByAccountIdShouldReturnAListOfTransactions () {
        List<Transaction> transactions = List.of(new Transaction(), new Transaction());
        List<TransactionDTO> transactionDtoList = List.of(new TransactionDTO(), new TransactionDTO());
        Integer accountId = 1;

        when(this.repository.findAllByAccountId(1)).thenReturn(transactions);
        List<TransactionDTO> transactionDtoToCompare = this.service.findAllByAccountId(accountId);

        assertEquals(transactionDtoList, transactionDtoToCompare);
    }
}
