package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.dtos.AccountDto;
import com.paymybuddy.paymybuddy.dtos.AccountVM;
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
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

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

        ParameterNotProvidedException exception = assertThrows(ParameterNotProvidedException.class, () -> this.service.save(null), message);

        Assertions.assertThat(exception.getMessage()).isEqualTo(message);
        Assertions.assertThat(exception.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
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

        Mockito.when(this.repository.findByEmailAndPassword(username, password)).thenReturn(account);
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

    @Test
    public void findAllDtoShouldReturnAListOfAccountDto() throws AccountNotFoundException {
        List<AccountDto> accountDtoList = new ArrayList<>();
        List<Account> accountList = new ArrayList<>();

        Mockito.when(this.repository.findAll()).thenReturn(accountList);
        Mockito.when(this.mapper.toDtoList(accountList)).thenReturn(accountDtoList);
        List<AccountDto> accountDtoListToCompare = this.service.findAllDto();

        Assertions.assertThat(accountDtoListToCompare).isEqualTo(accountDtoList);
    }

    @Test
    public void findAccountShouldReturnAnAccountDto() throws ParameterNotProvidedException, AccountNotFoundException {
        AccountDto accountDto = new AccountDto();
        Account account = new Account();
        account.setIdentifier(32L);
        accountDto.setIdentifier(32L);

        Mockito.when(this.repository.findByIdentifier(account.getIdentifier())).thenReturn(account);
        Mockito.when(this.mapper.toAccountDto(account)).thenReturn(accountDto);
        AccountDto accountDtoToCompare = this.service.findAccount(accountDto.getIdentifier());

        Assertions.assertThat(accountDtoToCompare).isEqualTo(accountDto);
        Assertions.assertThat(accountDtoToCompare.toString()).isEqualTo(accountDto.toString());
        Assertions.assertThat(accountDtoToCompare.hashCode()).isEqualTo(accountDto.hashCode());
    }

    @Test
    public void findAccountShouldThrowParameterNotProvidedException() throws ParameterNotProvidedException, AccountNotFoundException {
        String message = "Parameter not provided";

        ParameterNotProvidedException exception = assertThrows(ParameterNotProvidedException.class, () -> this.service.findAccount(null), message);

        Assertions.assertThat(exception.getMessage()).isEqualTo(message);
        Assertions.assertThat(exception.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void findAccountShouldThrowAccountNotFoundException() throws AccountNotFoundException {
        String message = "No account found";

        Mockito.when(this.repository.findByIdentifier(32L)).thenReturn(null);
        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> this.service.findAccount(32L), message);

        Assertions.assertThat(exception.getMessage()).isEqualTo(message);
        Assertions.assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void updateAccountShouldReturnAccountVM() throws AccountNotFoundException, ParameterNotProvidedException, BadRequestException {
        Account account = new Account();
        AccountVM accountVM = new AccountVM();
        AccountDto accountDto = new AccountDto();
        account.setIdentifier(32L);
        accountDto.setIdentifier(32L);
        accountVM.setIdentifier(32L);

        Mockito.when(this.repository.findById(account.getIdentifier())).thenReturn(Optional.of(account));
        Mockito.when(this.mapper.toModel(accountDto)).thenReturn(account);
        Mockito.when(this.repository.save(account)).thenReturn(account);
        Mockito.when(this.mapper.toAccountVM(account)).thenReturn(accountVM);
        AccountVM accountToCompare = this.service.updateAccount(accountDto);

        Assertions.assertThat(accountVM).isEqualTo(accountToCompare);
        Assertions.assertThat(accountVM.toString()).isEqualTo(accountToCompare.toString());
        Assertions.assertThat(accountVM.hashCode()).isEqualTo(accountToCompare.hashCode());
    }

    @Test
    public void findByNameShouldReturnAnAccountDto() throws AccountNotFoundException, ParameterNotProvidedException, BadRequestException {
        AccountDto accountDto = new AccountDto();
        Account account = new Account();
        account.setIdentifier(32L);
        accountDto.setIdentifier(32L);
        account.setName("name");
        accountDto.setName("name");

        Mockito.when(this.repository.findByName(account.getName())).thenReturn(account);
        Mockito.when(this.mapper.toAccountDto(account)).thenReturn(accountDto);
        AccountDto toCompare = this.service.findByName(accountDto.getName());

        Assertions.assertThat(toCompare).isEqualTo(accountDto);
        Assertions.assertThat(toCompare.toString()).isEqualTo(accountDto.toString());
        Assertions.assertThat(toCompare.hashCode()).isEqualTo(accountDto.hashCode());
    }
}
