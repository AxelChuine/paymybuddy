package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.exceptions.AccountAlreadyExistsException;
import com.paymybuddy.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.paymybuddy.exceptions.ParameterNotProvidedException;
import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.repository.IAccountRepository;
import org.apache.coyote.BadRequestException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService service;

    @Mock
    private IAccountRepository repository;


    @Test
    public void saveAccountShouldReturnAnAccount() throws AccountAlreadyExistsException, ParameterNotProvidedException, AccountNotFoundException {
        Account account = new Account();
        account.setIdentifier(32L);


        Mockito.when(this.repository.save(account)).thenReturn(account);
        Account accountToCompare = this.service.save(account);

        Assertions.assertThat(accountToCompare).isEqualTo(account);
        Assertions.assertThat(account.hashCode()).isEqualTo(accountToCompare.hashCode());
        Assertions.assertThat(account.toString()).isEqualTo(accountToCompare.toString());
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
    public void findByEmailShouldThrowParameterNotProvidedException() throws AccountNotFoundException, ParameterNotProvidedException {
        String message = "Parameter not provided";

        ParameterNotProvidedException exception = assertThrows(ParameterNotProvidedException.class, () -> this.service.findByEmail(null), message);

        Assertions.assertThat(exception.getMessage()).isEqualTo(message);
        Assertions.assertThat(exception.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void findByEmailShouldThrowNotFoundException() throws AccountNotFoundException, ParameterNotProvidedException {
        String message = "No account found";

        Mockito.when(this.repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> this.service.findByEmail(Mockito.anyString()), message);

        Assertions.assertThat(exception.getMessage()).isEqualTo(message);
        Assertions.assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void findByIdShouldReturnAnAccount() throws AccountNotFoundException, ParameterNotProvidedException {
        Account account = new Account();
        account.setIdentifier(32L);

        Mockito.when(this.repository.findById(account.getIdentifier())).thenReturn(Optional.of(account));

        Account accountToCompare = this.service.findById(account.getIdentifier());

        Assertions.assertThat(accountToCompare).isEqualTo(account);
        Assertions.assertThat(accountToCompare.toString()).isEqualTo(account.toString());
        Assertions.assertThat(accountToCompare.hashCode()).isEqualTo(account.hashCode());
    }

    @Test
    public void findByUsernameAndPasswordShouldReturnAnAccount() throws AccountNotFoundException, ParameterNotProvidedException, BadRequestException {
        Account account = new Account();
        account.setIdentifier(32L);
        String username = "babar";
        String password = "sjfgdhfbj";
        account.setPassword(password);
        account.setUsername(username);

        Mockito.when(this.repository.findByEmailAndPassword(username, password)).thenReturn(account);
        Account accountToCompare = this.service.findByUsernameAndPassword(username, password);

        Assertions.assertThat(accountToCompare).isEqualTo(account);
        Assertions.assertThat(accountToCompare.hashCode()).isEqualTo(account.hashCode());
        Assertions.assertThat(accountToCompare.toString()).isEqualTo(account.toString());
    }

    @Test
    public void findByUsernameShouldThrowParameterNotProvidedException() throws AccountNotFoundException, ParameterNotProvidedException {
        String message = "Aucun username ou mot de passe renseigné";

        BadRequestException exception = assertThrows(BadRequestException.class, () -> this.service.findByUsernameAndPassword(null, null), message);

        Assertions.assertThat(exception.getMessage()).isEqualTo(message);
    }

    @Test
    public void createAccountShouldReturnAnAccount() throws AccountAlreadyExistsException, ParameterNotProvidedException, AccountNotFoundException {
        Account account = new Account();
        String username = "username";
        String email = "test@test.com";
        String password = "password";
        account.setUsername(username);
        account.setEmail(email);
        account.setPassword(password);

        Mockito.when(this.repository.save(account)).thenReturn(account);
        Account accountToCompare = this.service.createAccount(email, username, password);

        Assertions.assertThat(accountToCompare).isEqualTo(account);
        Assertions.assertThat(accountToCompare.toString()).isEqualTo(account.toString());
        Assertions.assertThat(accountToCompare.hashCode()).isEqualTo(account.hashCode());
    }

    @Test
    public void findAccountShouldReturnAnAccountDto() throws ParameterNotProvidedException, AccountNotFoundException {
        Account account = new Account();
        account.setIdentifier(32L);

        Mockito.when(this.repository.findByIdentifier(account.getIdentifier())).thenReturn(account);
        Account accountToCompare = this.service.findAccount(account.getIdentifier());

        Assertions.assertThat(accountToCompare).isEqualTo(account);
        Assertions.assertThat(accountToCompare.toString()).isEqualTo(account.toString());
        Assertions.assertThat(accountToCompare.hashCode()).isEqualTo(account.hashCode());
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
    public void updateAccountShouldReturnAccountDto() throws AccountNotFoundException, ParameterNotProvidedException, BadRequestException {
        Account account = new Account();

        Mockito.when(this.repository.findById(account.getIdentifier())).thenReturn(Optional.of(account));
        Mockito.when(this.repository.save(account)).thenReturn(account);
        Account accountToCompare = this.service.updateAccount(account);

        Assertions.assertThat(account).isEqualTo(accountToCompare);
        Assertions.assertThat(account.toString()).isEqualTo(accountToCompare.toString());
        Assertions.assertThat(account.hashCode()).isEqualTo(accountToCompare.hashCode());
    }

    @Test
    public void updateAccountShouldThrowParameterNotProvidedException() throws ParameterNotProvidedException, AccountNotFoundException {
        String message = "Parameter not provided";

        ParameterNotProvidedException exception = assertThrows(ParameterNotProvidedException.class, () -> this.service.updateAccount(null), message);

        Assertions.assertThat(exception.getMessage()).isEqualTo(message);
        Assertions.assertThat(exception.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void updateAccountShouldThrowAccountNotFoundException() throws AccountNotFoundException {
        Account account = new Account();
        account.setIdentifier(32L);
        String message = "No account found";

        Mockito.when(this.repository.findById(32L)).thenReturn(Optional.empty());
        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> this.service.updateAccount(account), message);

        Assertions.assertThat(exception.getMessage()).isEqualTo(message);
        Assertions.assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void findByNameShouldReturnAnAccountDto() throws AccountNotFoundException, ParameterNotProvidedException, BadRequestException {
        Account account = new Account();
        account.setIdentifier(32L);
        account.setName("name");

        Mockito.when(this.repository.findByName(account.getName())).thenReturn(account);
        Account toCompare = this.service.findByName(account.getName());

        Assertions.assertThat(toCompare).isEqualTo(account);
        Assertions.assertThat(toCompare.toString()).isEqualTo(account.toString());
        Assertions.assertThat(toCompare.hashCode()).isEqualTo(account.hashCode());
    }

    @Test
    public void findByNameShouldThrowParameterNotProvidedException() throws ParameterNotProvidedException, AccountNotFoundException {
        String message = "No account found";

        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> this.service.findByName(null), message);

        Assertions.assertThat(exception.getMessage()).isEqualTo(message);
        Assertions.assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
