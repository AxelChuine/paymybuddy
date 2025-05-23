package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.models.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountRepositoryTest {

    @Autowired
    private IAccountRepository repository;

    @Test
    public void findAllShouldReturnAListOfAccounts() {
        List<Account> accounts = repository.findAll();

        Assertions.assertNotNull(accounts);
        Assertions.assertNotNull(accounts.getFirst());
    }

    @Test
    public void findByEmailAndPasswordShouldReturnAAccount() {
        Account account = this.repository.findByEmailAndPassword("jdubois@test.com", "1234");

        Assertions.assertNotNull(account);
        Assertions.assertNotNull(account.getIdentifier());
    }

    @Test
    public void findByEmailShouldReturnAnAccount () {
        Account account = this.repository.findByEmail("jdubois@test.com");

        Assertions.assertNotNull(account);
        Assertions.assertNotNull(account.getIdentifier());
    }
}
