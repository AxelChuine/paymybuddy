package com.paymybuddy.paymybuddy.services.mapper;

import com.paymybuddy.paymybuddy.dtos.AccountVM;
import com.paymybuddy.paymybuddy.dtos.TransactionDto;
import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.models.Transaction;
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

@ExtendWith(MockitoExtension.class)
public class TransactionMapperTest {
    @InjectMocks
    private TransactionMapper mapper;

    @Mock
    private AccountMapper accountMapper;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void toTransactionDtoShouldReturnTransactionDto() {
        LocalDateTime transactionDate = LocalDateTime.now();
        Transaction transaction = new Transaction();
        TransactionDto transactionDto = new TransactionDto();
        transaction.setIdentifier(0L);
        transaction.setName("test");
        transaction.setAmount(BigDecimal.TEN);
        transactionDto.setAmount(BigDecimal.TEN);
        transactionDto.setName("test");
        transactionDto.setIdentifier(0L);
        Account sender = new Account();
        Account receiver = new Account();
        AccountVM senderVM = new AccountVM();
        AccountVM receiverVM = new AccountVM();
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setTransactionDate(transactionDate);
        transactionDto.setSender(senderVM);
        transactionDto.setRecipient(receiverVM);
        transactionDto.setTransactionDate(transactionDate);

        Mockito.when(this.accountMapper.toAccountVM(sender)).thenReturn(senderVM);
        Mockito.when(this.accountMapper.toAccountVM(receiver)).thenReturn(receiverVM);
        TransactionDto transactionDtoToCompare = mapper.toTransactionDto(transaction);

        Assertions.assertEquals(transactionDto, transactionDtoToCompare);
    }
}
