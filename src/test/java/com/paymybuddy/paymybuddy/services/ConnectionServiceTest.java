package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.dtos.AccountDto;
import com.paymybuddy.paymybuddy.dtos.AccountVM;
import com.paymybuddy.paymybuddy.dtos.ConnectionDto;
import com.paymybuddy.paymybuddy.dtos.ConnectionVM;
import com.paymybuddy.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.paymybuddy.exceptions.ParameterNotProvidedException;
import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.models.Connection;
import com.paymybuddy.paymybuddy.repository.IConnectionRepository;
import com.paymybuddy.paymybuddy.services.mapper.AccountMapper;
import com.paymybuddy.paymybuddy.services.mapper.ConnectionMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class ConnectionServiceTest {
    @InjectMocks
    private ConnectionService service;

    @Mock
    private AccountService accountService;

    @Mock
    private IConnectionRepository repository;

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private ConnectionMapper mapper;

    private final Long accountId = 1L;
    private final String firstName = "firstName";
    private final String lastName = "lastName";
    private final String username = "username";
    private final String password = "1234";
    private final String email = "email@email.com";
    private final String name = "name";

    private final BigDecimal balance = new BigDecimal("100");
    private final Set<Account> connections = new HashSet<>();
    private final Set<AccountDto> connectionDtoSet = new HashSet<>();

    private final Long connectionId = 2L;
    private final String firstNameConnection = "firstName";
    private final String lastNameConnection = "lastName";
    private final String usernameConnection = "username";
    private final String passwordConnection = "1234";
    private final String emailConnection = "email-connection@email.com";
    private final String nameConnection = "name";

    private AccountDto accountDto = new AccountDto(
            this.accountId,
            this.firstName,
            this.lastName,
            this.username,
            this.password,
            this.email,
            this.name,
            this.balance,
            this.connectionDtoSet
    );

    private Account account = new Account(
            this.accountId,
            this.firstName,
            this.lastName,
            this.username,
            this.password,
            this.email,
            this.name,
            this.balance,
            this.connections
    );

    private Account connectionAccount = new Account(
            this.connectionId,
            this.firstNameConnection,
            this.lastNameConnection,
            this.usernameConnection,
            this.passwordConnection,
            this.emailConnection,
            this.nameConnection,
            this.balance,
            this.connections
    );

    private AccountVM accountVM = new AccountVM(
            this.accountId,
            this.firstName,
            this.lastName,
            this.email,
            this.name,
            this.balance
    );

    private AccountDto connectionDtoAccount = new AccountDto(
            this.connectionId,
            this.firstNameConnection,
            this.lastNameConnection,
            this.usernameConnection,
            this.passwordConnection,
            this.emailConnection,
            this.nameConnection,
            this.balance,
            this.connectionDtoSet
    );

    private AccountVM connectionVMAccount = new AccountVM(
            this.connectionId,
            this.firstNameConnection,
            this.lastNameConnection,
            this.emailConnection,
            this.nameConnection,
            this.balance
    );

    private Connection connection = new Connection(account, connectionAccount);
    private ConnectionDto connectionDto = new ConnectionDto(accountDto, connectionDtoAccount);
    private ConnectionVM connectionVM = new ConnectionVM(accountId, connectionId, firstNameConnection, lastNameConnection);
    private List<ConnectionVM> connectionVMList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        this.connectionVMList.add(connectionVM);
    }

    @Test
    public void createConnectionShouldReturnAConnectionDto() throws AccountNotFoundException, ParameterNotProvidedException {
        Mockito.when(this.accountMapper.toModel(this.accountDto)).thenReturn(this.account);
        Mockito.when(this.accountMapper.toModel(this.connectionDtoAccount)).thenReturn(this.connectionAccount);
        Mockito.when(this.repository.save(this.connection)).thenReturn(this.connection);
        Mockito.when(this.mapper.toDto(this.connection)).thenReturn(this.connectionDto);
        ConnectionDto toCompare = this.service.create(accountDto, connectionDtoAccount);

        Assertions.assertThat(toCompare).isEqualTo(this.connectionDto);
        Assertions.assertThat(toCompare.hashCode()).isEqualTo(this.connectionDto.hashCode());
        Assertions.assertThat(toCompare.toString()).isEqualTo(this.connectionDto.toString());
    }
}
