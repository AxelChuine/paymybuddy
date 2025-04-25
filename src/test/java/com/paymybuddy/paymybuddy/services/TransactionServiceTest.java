package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.dtos.AccountDto;
import com.paymybuddy.paymybuddy.dtos.ConnectionDto;
import com.paymybuddy.paymybuddy.dtos.TransactionDto;
import com.paymybuddy.paymybuddy.exceptions.AccountAlreadyExistsException;
import com.paymybuddy.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.paymybuddy.exceptions.ParameterNotProvidedException;
import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.models.Transaction;
import com.paymybuddy.paymybuddy.repository.IAccountRepository;
import com.paymybuddy.paymybuddy.repository.ITransactionRepository;
import com.paymybuddy.paymybuddy.services.mapper.AccountMapper;
import com.paymybuddy.paymybuddy.services.mapper.TransactionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

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

    @Mock
    private ConnectionService connectionService;

    @Mock
    private IAccountRepository accountRepository;

    private final Long id = 1L;

    private final String name = "test";

    private final Long senderId = 1L;
    private final Long recipientId = 2L;
    private final String nameSender = "sender";
    private final String nameRecipient = "recipient";
    private final String senderUsername = "senderUsername";
    private final String recipientUsername = "recipientUsername";
    private final String senderEmail = "test-sender@paymybuddy.com";
    private final String recipientEmail = "test-recipient@paymybuddy.com";
    private final String password = "1234";
    private final Set<Account> connections = new HashSet<>();
    private final Set<AccountDto> connectionDtoSet = new HashSet<>();


    private BigDecimal amount = new BigDecimal("100.00");

    private final LocalDateTime transactionDate = LocalDateTime.now();

    private Account sender = new Account(
            this.senderId,
            this.senderUsername,
            this.password,
            this.senderEmail,
            this.nameSender,
            this.amount,
            this.connections
    );

    private Account recipient = new Account(
            this.recipientId,
            this.recipientUsername,
            this.password,
            this.recipientEmail,
            this.nameRecipient,
            this.amount,
            this.connections
    );

    private AccountDto senderDto = new AccountDto(
            this.senderId,
            this.senderUsername,
            this.password,
            this.senderEmail,
            this.nameSender,
            this.amount,
            this.connectionDtoSet
    );

    private AccountDto recipientDto = new AccountDto(
            this.recipientId,
            this.recipientUsername,
            this.password,
            this.recipientEmail,
            this.nameRecipient,
            this.amount,
            this.connectionDtoSet
    );


    private Transaction transaction = new Transaction(
            this.id,
            this.name,
            this.amount,
            this.sender,
            this.recipient,
            this.transactionDate
    );

    private TransactionDto transactionDto = new TransactionDto(
            this.id,
            this.name,
            this.amount,
            this.senderDto,
            this.recipientDto,
            this.transactionDate
    );

    private List<TransactionDto> transactionDtoList = new ArrayList<>();

    private List<Transaction> transactionList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        this.transactionList.add(this.transaction);
        this.transactionDtoList.add(this.transactionDto);
    }

    @Test
    public void createTransactionDtoShouldReturnATransactionDto() throws ParameterNotProvidedException, AccountNotFoundException, AccountAlreadyExistsException, AccountNotFoundException {
        AccountDto payMyBuddy = new AccountDto();
        payMyBuddy.setIdentifier(1L);
        payMyBuddy.setBalance(BigDecimal.ZERO);

        ConnectionDto connectionPayMyBuddySender = new ConnectionDto(payMyBuddy, senderDto);
        ConnectionDto connectionPayMyBuddyRecipient = new ConnectionDto(payMyBuddy, recipientDto);
        ConnectionDto connectionSenderDto = new ConnectionDto(senderDto, recipientDto);
        ConnectionDto connectionRecipientDto = new ConnectionDto(recipientDto, senderDto);

        Mockito.when(this.accountService.findByName(Mockito.anyString())).thenReturn(payMyBuddy);
        Mockito.when(this.accountService.save(payMyBuddy)).thenReturn(payMyBuddy);
        Mockito.when(this.accountService.save(senderDto)).thenReturn(senderDto);
        Mockito.when(this.accountService.save(recipientDto)).thenReturn(recipientDto);
        Mockito.when(this.connectionService.create(payMyBuddy, senderDto)).thenReturn(connectionPayMyBuddySender);
        Mockito.when(this.connectionService.create(payMyBuddy, recipientDto)).thenReturn(connectionPayMyBuddyRecipient);
        Mockito.when(this.connectionService.create(senderDto, recipientDto)).thenReturn(connectionSenderDto);
        Mockito.when(this.connectionService.create(recipientDto, senderDto)).thenReturn(connectionRecipientDto);
        Mockito.when(this.mapper.toModel(transactionDto)).thenReturn(transaction);
        Mockito.when(this.repository.save(transaction)).thenReturn(transaction);
        Mockito.when(this.mapper.toTransactionDto(this.transaction)).thenReturn(this.transactionDto);
        TransactionDto toCompare = this.service.create(transactionDto);

        assertThat(transactionDto).isEqualTo(toCompare);
        assertThat(transactionDto.toString()).isEqualTo(toCompare.toString());
        assertThat(transactionDto.hashCode()).isEqualTo(toCompare.hashCode());

    }

    @Test
    public void findAllByAccountIdShouldReturnAListOfTransactionDto() throws ParameterNotProvidedException, AccountNotFoundException {
        Mockito.when(this.accountRepository.findById(1L)).thenReturn(Optional.of(this.sender));
        Mockito.when(this.repository.findAllByAccountId(1L)).thenReturn(this.transactionList);
        Mockito.when(this.mapper.toTransactionDtoList(this.transactionList)).thenReturn(this.transactionDtoList);
        List<TransactionDto> listToCompare = this.service.findAllByAccountId(1L);

        assertThat(listToCompare).isEqualTo(this.transactionDtoList);
    }
}
