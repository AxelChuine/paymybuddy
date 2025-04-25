package com.paymybuddy.paymybuddy.services.mapper;

import com.paymybuddy.paymybuddy.dtos.AccountDto;

import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.repository.IAccountRepository;
import com.paymybuddy.paymybuddy.services.AccountService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class AccountMapperTest {
    @InjectMocks
    private AccountMapper mapper;

    @Mock
    private AccountService service;

    @Mock
    private IAccountRepository repository;

    private final Long id = 1L;
    private final String username = "userName";
    private final String email = "email@email.com";
    private final String password = "password";
    private final String name = "name";
    private final BigDecimal balance = BigDecimal.TEN;
    private List<Account> accountList = new ArrayList<>();
    private List<AccountDto> accountDtoList = new ArrayList<>();
    private Set<Account> connections = new HashSet<>();
    private Set<AccountDto> connectionDtoSet = new HashSet<>();

    private Account account;
    private AccountDto accountDto;

    @BeforeEach
    public void setUp() {
        account = new Account(
                this.id,
                this.username,
                this.password,
                this.email,
                this.name,
                this.balance,
                null
        );
        accountDto = new AccountDto(
                this.id,
                this.username,
                this.password,
                this.email,
                this.name,
                this.balance,
                null
        );
        connections.add(account);
        connectionDtoSet.add(accountDto);
        this.accountList.add(account);
        this.accountDtoList.add(accountDto);
    }

    @Test
    public void toModelShouldReturnAccount() {
        Mockito.when(this.repository.findById(this.id)).thenReturn(Optional.of(this.account));
        Account toCompare = this.mapper.toModel(this.accountDto);

        Assertions.assertThat(toCompare).isEqualTo(this.account);
        Assertions.assertThat(toCompare.toString()).isEqualTo(this.account.toString());
        Assertions.assertThat(toCompare.hashCode()).isEqualTo(this.account.hashCode());
    }

    @Test
    public void toConnectionModelSetShouldReturnASetOfConnections() {
        Set<Account> toCompare = this.mapper.toConnectionModelSet(this.connectionDtoSet);

        Assertions.assertThat(toCompare).isEqualTo(this.connections);
    }

    @Test
    public void toConnectionModelShouldReturnAnAccount() {
        Account toCompare = this.mapper.toConnectionModel(this.accountDto);

        Assertions.assertThat(toCompare).isEqualTo(this.account);
        Assertions.assertThat(toCompare.toString()).isEqualTo(this.account.toString());
        Assertions.assertThat(toCompare.hashCode()).isEqualTo(this.account.hashCode());
    }

    @Test
    public void toAccountDtoShouldReturnAnAccountDto() {
        AccountDto toCompare = this.mapper.toAccountDto(this.account);

        Assertions.assertThat(toCompare).isEqualTo(this.accountDto);
        Assertions.assertThat(toCompare.toString()).isEqualTo(this.accountDto.toString());
        Assertions.assertThat(toCompare.hashCode()).isEqualTo(this.accountDto.hashCode());
    }

    @Test
    public void toDtoListShouldReturnAListOfAccountDto() {
        List<AccountDto> toCompare = this.mapper.toDtoList(this.accountList);

        Assertions.assertThat(toCompare).isEqualTo(this.accountDtoList);
    }
}
