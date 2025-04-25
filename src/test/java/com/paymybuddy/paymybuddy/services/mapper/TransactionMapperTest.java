package com.paymybuddy.paymybuddy.services.mapper;

import com.paymybuddy.paymybuddy.dtos.AccountDto;
import com.paymybuddy.paymybuddy.dtos.TransactionDto;
import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.models.Transaction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TransactionMapperTest {
    @InjectMocks
    private TransactionMapper mapper;

    @Mock
    private AccountMapper accountMapper;

    private final Long transactionId = 1L;
    private final LocalDateTime transactionDate = LocalDateTime.now();
    private final BigDecimal amount = BigDecimal.TEN;
    private final String nameTransaction = "nameTransaction";

    private Account sender;
    private AccountDto senderDto;
    private Account recipient;
    private AccountDto recipientDto;
    private Transaction transaction;
    private TransactionDto transactionDto;
    private List<Transaction> transactionList = new ArrayList<>();
    private List<TransactionDto> transactionDtoList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
            this.transaction = new Transaction(
                    this.transactionId,
                    this.nameTransaction,
                    this.amount,
                    this.sender,
                    this.recipient,
                    this.transactionDate
            );
        this.transactionDto = new TransactionDto(
                this.transactionId,
                this.nameTransaction,
                this.amount,
                this.senderDto,
                this.recipientDto,
                this.transactionDate
        );
        this.transactionList.add(this.transaction);
        this.transactionDtoList.add(this.transactionDto);
    }

    @Test
    public void toTransactionDtoShouldReturnTransactionDto() {
        TransactionDto toCompare = mapper.toTransactionDto(transaction);

        Assertions.assertThat(toCompare).isEqualTo(this.transactionDto);
        Assertions.assertThat(toCompare.toString()).isEqualTo(this.transactionDto.toString());
        Assertions.assertThat(toCompare.hashCode()).isEqualTo(this.transactionDto.hashCode());
    }

    @Test
    public void toModelShouldReturnTransaction() {
        Transaction toCompare = this.mapper.toModel(this.transactionDto);

        Assertions.assertThat(toCompare).isEqualTo(this.transaction);
        Assertions.assertThat(toCompare.toString()).isEqualTo(this.transaction.toString());
        Assertions.assertThat(toCompare.hashCode()).isEqualTo(this.transaction.hashCode());
    }

    @Test
    public void toDtoListShouldReturnTransactionDtoList() {
        List<TransactionDto> toCompare = this.mapper.toTransactionDtoList(transactionList);

        Assertions.assertThat(toCompare).isEqualTo(this.transactionDtoList);
    }
}
