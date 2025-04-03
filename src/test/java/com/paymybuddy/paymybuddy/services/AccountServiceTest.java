package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.dtos.AccountDto;
import com.paymybuddy.paymybuddy.exceptions.AccountAlreadyExistsException;
import com.paymybuddy.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.paymybuddy.exceptions.ParameterNotProvidedException;
import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.repository.IAccountRepository;
import com.paymybuddy.paymybuddy.services.mapper.AccountMapper;
import org.apache.coyote.BadRequestException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Test
    public void saveShouldThrowParameterNotProvidedException() throws AccountAlreadyExistsException, ParameterNotProvidedException {
        String message = "Parameter not provided";
    }

    @Test
    public void findByEmailShouldReturnAnAccount() throws AccountNotFoundException, ParameterNotProvidedException {
        Account account = new Account();
        account.setIdentifier(32L);
        String email = "jhdhvjdjg";
        account.setEmail(email);

        Mockito.when(this.repository.findByEmail(email)).thenReturn(Optional.of(account));
        Optional<Account> optional = this.service.findByEmail(email);

        org.junit.jupiter.api.Assertions.assertTrue(optional.isPresent());
    }

    @Test
    public void findByIdShouldReturnAnAccount() throws AccountNotFoundException, ParameterNotProvidedException {
        Account account = new Account();
        account.setIdentifier(32L);
        AccountDto accountDto = new AccountDto();
        accountDto.setIdentifier(32L);

        Mockito.when(this.repository.findById(account.getIdentifier())).thenReturn(Optional.of(account));
        Mockito.when(this.mapper.toAccountDto(account)).thenReturn(accountDto);
        AccountDto accountToCompare = this.service.findById(account.getIdentifier());

        Assertions.assertThat(accountToCompare).isEqualTo(accountDto);
        Assertions.assertThat(accountToCompare.toString()).isEqualTo(accountDto.toString());
        Assertions.assertThat(accountToCompare.hashCode()).isEqualTo(accountDto.hashCode());
    }

    @Test
    public void findByUsernameAndPasswordShouldReturnAnAccount() throws AccountNotFoundException, ParameterNotProvidedException, BadRequestException {
        Account account = new Account();
        account.setIdentifier(32L);
        String username = "babar";
        String password = "sjfgdhfbj";
        account.setPassword(password);
        account.setUsername(username);
        AccountDto accountDto = new AccountDto();
        accountDto.setIdentifier(32L);
        accountDto.setUsername(username);

        Mockito.when(this.repository.findByEmailAndPassword(username, password)).thenReturn(Optional.of(account));
        Mockito.when(this.mapper.toAccountDto(account)).thenReturn(accountDto);
        AccountDto accountToCompare = this.service.findByUsernameAndPassword(username, password);

        Assertions.assertThat(accountToCompare).isEqualTo(accountDto);
        Assertions.assertThat(accountToCompare.hashCode()).isEqualTo(accountDto.hashCode());
        Assertions.assertThat(accountToCompare.toString()).isEqualTo(accountDto.toString());
    }

    @Test
    public void createAccountShouldReturnAnAccount() throws AccountAlreadyExistsException, ParameterNotProvidedException, AccountNotFoundException {
        AccountDto accountDto = new AccountDto();
        Account account = new Account();
        String username = "username";
        String email = "test@test.com";
        String password = "password";
        account.setUsername(username);
        account.setEmail(email);
        account.setPassword(password);
        accountDto.setUsername(username);
        accountDto.setEmail(email);
        accountDto.setPassword(password);

        Mockito.when(this.repository.save(account)).thenReturn(account);
        Mockito.when(this.mapper.toAccountDto(account)).thenReturn(accountDto);
        AccountDto accountToCompare = this.service.createAccount(email, username, password);

        Assertions.assertThat(accountToCompare).isEqualTo(accountDto);
        Assertions.assertThat(accountToCompare.toString()).isEqualTo(accountDto.toString());
        Assertions.assertThat(accountToCompare.hashCode()).isEqualTo(accountDto.hashCode());
    }

    @Test
    public void findAllShouldReturnAListOfAccountDto() throws AccountNotFoundException {
        List<Account> accountList = new ArrayList<>();

        Mockito.when(this.repository.findAll()).thenReturn(accountList);
        List<Account> accountListToCompare = this.service.findAll();

        Assertions.assertThat(accountListToCompare).isEqualTo(accountList);
    }
}
