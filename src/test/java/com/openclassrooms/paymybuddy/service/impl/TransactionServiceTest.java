package com.openclassrooms.paymybuddy.service.impl;

import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.repository.ITransactionRepository;
import com.openclassrooms.paymybuddy.service.ITransactionService;
import com.openclassrooms.paymybuddy.service.dto.TransactionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TransactionServiceTest {

    @MockBean
    private ITransactionRepository repository;

    @Autowired
    private ITransactionService service;


    @BeforeEach
    public void setTransaction () {
        Transaction transaction = new Transaction();
    }

    @Test
    public void findAllTransactionsByAccountIdShouldReturnAListOfTransactions () {
        List<Transaction> transactions = List.of(new Transaction(), new Transaction());
        List<TransactionDTO> transactionDtoList = List.of(new TransactionDTO(), new TransactionDTO());

        when(this.repository.findAllByAccountId(1)).thenReturn(transactions);
        List<TransactionDTO> transactionDtoToCompare = this.service.findAllByAccountId();

        assertEquals(transactionDtoList, transactionDtoToCompare);
    }
}
