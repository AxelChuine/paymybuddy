package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.exceptions.AccountAlreadyExistsException;
import com.paymybuddy.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.paymybuddy.exceptions.ParameterNotProvidedException;
import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.models.Connection;
import com.paymybuddy.paymybuddy.models.Transaction;
import com.paymybuddy.paymybuddy.repository.IAccountRepository;
import com.paymybuddy.paymybuddy.repository.ITransactionRepository;
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

    private Transaction transaction = new Transaction(
            this.id,
            this.name,
            this.amount,
            this.sender,
            this.recipient,
            this.transactionDate
    );

    private List<Transaction> transactionList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        this.transactionList.add(this.transaction);
    }

    @Test
    public void createTransactionDtoShouldReturnATransactionDto() throws ParameterNotProvidedException, AccountNotFoundException, AccountAlreadyExistsException, AccountNotFoundException {
        Account payMyBuddy = new Account();
        payMyBuddy.setIdentifier(1L);
        payMyBuddy.setBalance(BigDecimal.ZERO);

        Connection connectionPayMyBuddySender = new Connection(payMyBuddy, sender);
        Connection connectionPayMyBuddyRecipient = new Connection(payMyBuddy, recipient);

        Mockito.when(this.accountService.findByName(Mockito.anyString())).thenReturn(payMyBuddy);
        Mockito.when(this.accountService.save(payMyBuddy)).thenReturn(payMyBuddy);
        Mockito.when(this.accountService.save(sender)).thenReturn(sender);
        Mockito.when(this.accountService.save(recipient)).thenReturn(recipient);
        Mockito.when(this.accountService.getAccount()).thenReturn(sender);
        Mockito.when(this.accountService.findAccount(recipientId)).thenReturn(recipient);
        Mockito.when(this.connectionService.create(payMyBuddy, sender)).thenReturn(connectionPayMyBuddySender);
        Mockito.when(this.connectionService.create(payMyBuddy, recipient)).thenReturn(connectionPayMyBuddyRecipient);
        Mockito.when(this.repository.save(transaction)).thenReturn(transaction);
        Transaction toCompare = this.service.create(transaction);

        assertThat(transaction).isEqualTo(toCompare);
        assertThat(transaction.toString()).isEqualTo(toCompare.toString());
        assertThat(transaction.hashCode()).isEqualTo(toCompare.hashCode());

    }

    @Test
    public void findAllByAccountIdShouldReturnAListOfTransactionDto() throws ParameterNotProvidedException, AccountNotFoundException {
        Mockito.when(this.accountRepository.findById(1L)).thenReturn(Optional.of(this.sender));
        Mockito.when(this.repository.findAllByAccountId(1L)).thenReturn(this.transactionList);
        List<Transaction> listToCompare = this.service.findAllByAccountId(1L);

        assertThat(listToCompare).isEqualTo(this.transactionList);
    }
}
