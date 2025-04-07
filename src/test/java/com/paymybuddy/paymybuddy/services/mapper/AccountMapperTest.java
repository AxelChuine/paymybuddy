package com.paymybuddy.paymybuddy.services.mapper;

import com.paymybuddy.paymybuddy.dtos.AccountDto;
import com.paymybuddy.paymybuddy.dtos.AccountVM;
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
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AccountMapperTest {
    @InjectMocks
    private AccountMapper mapper;

    @Mock
    private AccountService service;

    @Mock
    private IAccountRepository repository;

    private final Long id = 1L;
    private final String firstName = "firstName";
    private final String lastName = "lastName";
    private final String username = "userName";
    private final String email = "email@email.com";
    private final String password = "password";
    private final String name = "name";
    private final BigDecimal balance = BigDecimal.TEN;

    private Account account;
    private AccountDto accountDto;
    private AccountVM accountVM;

    @BeforeEach
    public void setUp() {
        account = new Account(
                this.id,
                this.firstName,
                this.lastName,
                this.username,
                this.password,
                this.email,
                this.name,
                this.balance,
                null
        );
        accountDto = new AccountDto(
                this.id,
                this.firstName,
                this.lastName,
                this.username,
                this.password,
                this.email,
                this.name,
                this.balance,
                null
        );
        this.accountVM = new AccountVM(
                this.id,
                this.firstName,
                this.lastName,
                this.email,
                this.name,
                this.balance
        );
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
    public void toVMShouldReturnAnAccountVM() {
        AccountVM toCompare = this.mapper.toAccountVM(this.account);

        Assertions.assertThat(toCompare).isEqualTo(this.accountVM);
        Assertions.assertThat(toCompare.toString()).isEqualTo(this.accountVM.toString());
        Assertions.assertThat(toCompare.hashCode()).isEqualTo(this.accountVM.hashCode());
    }
}
