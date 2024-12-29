package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.models.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TransactionRepositoryTest {
    @Autowired
    private ITransactionRepository repository;

    @Test
    public void findAllShouldReturnAllTransactions() {
        List<Transaction> transactions = repository.findAll();

        assertThat(transactions).isNotNull();
        assertThat(transactions.getFirst()).isNotNull();
    }
}
