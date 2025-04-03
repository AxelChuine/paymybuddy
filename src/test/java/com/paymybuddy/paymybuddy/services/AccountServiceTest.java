package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.dtos.AccountDto;
import com.paymybuddy.paymybuddy.exceptions.AccountAlreadyExistsException;
import com.paymybuddy.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.paymybuddy.exceptions.ParameterNotProvidedException;
import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.repository.IAccountRepository;
import com.paymybuddy.paymybuddy.services.mapper.AccountMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService service;

    @Mock
    private IAccountRepository repository;

    @Mock
    private AccountMapper mapper;

    @Test
    public void saveAccountShouldReturnAnAccount() throws AccountAlreadyExistsException, ParameterNotProvidedException, AccountNotFoundException {
        AccountDto accountDto = new AccountDto();
        accountDto.setIdentifier(32L);
        Account account = new Account();
        account.setIdentifier(32L);

        Mockito.when(this.mapper.toModel(accountDto)).thenReturn(account);
        Mockito.when(this.repository.save(account)).thenReturn(account);
        Mockito.when(this.mapper.toAccountDto(account)).thenReturn(accountDto);
        AccountDto accountToCompare = this.service.save(accountDto);

        Assertions.assertThat(accountToCompare).isEqualTo(accountDto);
        Assertions.assertThat(accountDto.hashCode()).isEqualTo(accountToCompare.hashCode());
        Assertions.assertThat(accountDto.toString()).isEqualTo(accountToCompare.toString());
    }
}
