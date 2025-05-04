package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.paymybuddy.exceptions.ParameterNotProvidedException;
import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.models.Connection;
import com.paymybuddy.paymybuddy.repository.IAccountRepository;
import com.paymybuddy.paymybuddy.repository.IConnectionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ConnectionServiceTest {
    @InjectMocks
    private ConnectionService service;

    @Mock
    private AccountService accountService;

    @Mock
    private IAccountRepository accountRepository;

    @Mock
    private IConnectionRepository repository;


    private final Long accountId = 1L;
    private final String username = "username";
    private final String password = "1234";
    private final String email = "email@email.com";
    private final String name = "name";

    private final BigDecimal balance = new BigDecimal("100");
    private final Set<Account> connections = new HashSet<>();

    private final Long connectionId = 2L;
    private final String usernameConnection = "username";
    private final String passwordConnection = "1234";
    private final String emailConnection = "email-connection@email.com";
    private final String nameConnection = "name";

    private Account account = new Account(
            this.accountId,
            this.username,
            this.password,
            this.email,
            this.name,
            this.balance,
            this.connections
    );

    private Account connectionAccount = new Account(
            this.connectionId,
            this.usernameConnection,
            this.passwordConnection,
            this.emailConnection,
            this.nameConnection,
            this.balance,
            this.connections
    );

    private Connection connection = new Connection(account, connectionAccount);
    private List<Connection> connectionList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        this.connectionList.add(connection);
    }

    @Test
    public void createConnectionShouldReturnAConnectionDto() throws AccountNotFoundException, ParameterNotProvidedException {
        Mockito.when(this.repository.save(this.connection)).thenReturn(this.connection);
        Connection toCompare = this.service.create(account, connectionAccount);

        Assertions.assertThat(toCompare).isEqualTo(this.connection);
        Assertions.assertThat(toCompare.hashCode()).isEqualTo(this.connection.hashCode());
        Assertions.assertThat(toCompare.toString()).isEqualTo(this.connection.toString());
    }

    @Test
    public void findAllByAccountShouldReturnAListOfConnectionVM() throws AccountNotFoundException, ParameterNotProvidedException {
        Mockito.when(this.accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        Mockito.when(this.repository.findAllByAccount(account)).thenReturn(this.connectionList);
        List<Connection> listToCompare = this.service.findAllByAccount(account);

        Assertions.assertThat(listToCompare).isEqualTo(this.connectionList);
    }

    @Test
    public void findAllByAccountShouldThrowParameterNotProvidedException() throws AccountNotFoundException, ParameterNotProvidedException {
        String message = "Parameter not provided";

        ParameterNotProvidedException exception = assertThrows(ParameterNotProvidedException.class, () -> this.service.findAllByAccount(null), message);

        Assertions.assertThat(exception.getMessage()).isEqualTo(message);
        Assertions.assertThat(exception.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void findAllConnectionsByAccountShouldReturnEmptyList() throws AccountNotFoundException, ParameterNotProvidedException {
        List<Connection> emptyConnectionList = new ArrayList<>();

        Mockito.when(this.accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        Mockito.when(this.repository.findAllByAccount(account)).thenReturn(emptyConnectionList);
        List<Connection> listToCompare = this.service.findAllByAccount(account);

        Assertions.assertThat(listToCompare).isEmpty();
        Assertions.assertThat(listToCompare).isEqualTo(emptyConnectionList);
    }

    @Test
    public void createAccountShouldThrowParameterNotProvidedException() throws AccountNotFoundException, ParameterNotProvidedException {
        String message = "Parameter not provided";

        ParameterNotProvidedException exception = assertThrows(ParameterNotProvidedException.class, () -> this.service.create(null, null), message);

        Assertions.assertThat(exception.getMessage()).isEqualTo(message);
        Assertions.assertThat(exception.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
