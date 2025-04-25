package com.paymybuddy.paymybuddy.services.mapper;

import com.paymybuddy.paymybuddy.dtos.AccountDto;
import com.paymybuddy.paymybuddy.dtos.ConnectionDto;
import com.paymybuddy.paymybuddy.dtos.ConnectionVM;
import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.models.Connection;
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
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ConnectionMapperTest {
    @InjectMocks
    private ConnectionMapper mapper;

    @Mock
    private AccountMapper accountMapper;

    private final Long accountId = 1L;
    private final Long connectionId = 2L;
    private final String firstName = "firstName";
    private final String firstNameConnection = "firstNameConnection";
    private final String lastName = "lastName";
    private final String lastNameConnection = "lastNameConnection";
    private final String username = "lastName";
    private final String usernameConnection = "lastNameConnection";
    private final String password = "password";
    private final String email = "email";
    private final String emailConnection = "emailConnection";
    private final String name = "name";
    private final String nameConnection = "nameConnection";
    private final BigDecimal balance = BigDecimal.TEN;

    private Account account;
    private AccountDto accountDto;
    private Account accountConnection;
    private AccountDto accountConnectionDto;
    private List<Connection> connectionList = new ArrayList();
    private List<ConnectionDto> connectionDtoList = new ArrayList();
    private List<ConnectionVM> connectionVMList = new ArrayList();
    private Connection connection;
    private ConnectionDto connectionDto;
    private ConnectionVM connectionVM;


    @BeforeEach
    public void setUp() {
        this.account = new Account(
                this.accountId,
                this.username,
                this.password,
                this.email,
                this.name,
                this.balance,
                null
        );
        this.accountDto = new AccountDto(
                this.accountId,
                this.username,
                this.password,
                this.email,
                this.name,
                this.balance,
                null
        );
        this.accountConnection = new Account(
                this.connectionId,
                this.usernameConnection,
                this.password,
                this.emailConnection,
                this.name,
                this.balance,
                null
        );
        this.accountConnectionDto = new AccountDto(
                this.connectionId,
                this.usernameConnection,
                this.password,
                this.emailConnection,
                this.name,
                this.balance,
                null
        );
        this.connection = new Connection(
                this.account,
                this.accountConnection
        );
        this.connectionDto = new ConnectionDto(
                this.accountDto,
                this.accountConnectionDto
        );
        this.connectionVM = new ConnectionVM(
               this.accountId,
               this.connectionId,
               this.usernameConnection
        );
        this.connectionList.add(connection);
        this.connectionDtoList.add(connectionDto);
        this.connectionVMList.add(connectionVM);
    }

    @Test
    public void toDtoShouldReturnAConnectionDto() {
        Mockito.when(this.accountMapper.toAccountDto(this.account)).thenReturn(this.accountDto);
        Mockito.when(this.accountMapper.toAccountDto(this.accountConnection)).thenReturn(this.accountConnectionDto);
        ConnectionDto toCompare = mapper.toDto(this.connection);

        Assertions.assertThat(toCompare).isEqualTo(connectionDto);
        Assertions.assertThat(toCompare.toString()).isEqualTo(connectionDto.toString());
        Assertions.assertThat(toCompare.hashCode()).isEqualTo(connectionDto.hashCode());
    }

    @Test
    public void toVMDtoShouldReturnAConnectionVM() {
        ConnectionVM toCompare = this.mapper.toVM(this.connection);

        Assertions.assertThat(toCompare).isEqualTo(connectionVM);
        Assertions.assertThat(toCompare.toString()).isEqualTo(connectionVM.toString());
        Assertions.assertThat(toCompare.hashCode()).isEqualTo(connectionVM.hashCode());
    }

    @Test
    public void toVMListShouldReturnAConnectionVMList() {
        List<ConnectionVM> toCompare = this.mapper.toVMList(this.connectionList);

        Assertions.assertThat(toCompare).isEqualTo(connectionVMList);
    }

    @Test
    public void toModelShouldReturnAConnection() {
        Mockito.when(this.accountMapper.toModel(this.accountDto)).thenReturn(this.account);
        Mockito.when(this.accountMapper.toModel(this.accountConnectionDto)).thenReturn(this.accountConnection);
        Connection toCompare = this.mapper.toModel(this.connectionDto);

        Assertions.assertThat(toCompare).isEqualTo(this.connection);
        Assertions.assertThat(toCompare.toString()).isEqualTo(connection.toString());
        Assertions.assertThat(toCompare.hashCode()).isEqualTo(connection.hashCode());
    }
}
