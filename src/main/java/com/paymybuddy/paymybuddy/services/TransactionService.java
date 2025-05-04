package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.exceptions.AccountAlreadyExistsException;
import com.paymybuddy.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.paymybuddy.exceptions.ParameterNotProvidedException;
import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.models.Transaction;
import com.paymybuddy.paymybuddy.repository.IAccountRepository;
import com.paymybuddy.paymybuddy.repository.ITransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private final ITransactionRepository repository;

    private final AccountService accountService;

    private final IAccountRepository accountRepository;

    private final ConnectionService connectionService;

    public TransactionService(ITransactionRepository repository, AccountService accountService, IAccountRepository accountRepository, ConnectionService connectionService) {
        this.repository = repository;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.connectionService = connectionService;
    }

    /**
     * Service où se trouve la méthodes de paiement:
     * échange d'argent entre le compte A et le compte B et création de l'objet en base de données
     * les frais de gestion sont payés
     */
    @Transactional
    public Transaction create(final Transaction transaction) throws ParameterNotProvidedException, AccountNotFoundException, AccountAlreadyExistsException {
        BigDecimal fee = transaction.getAmount().multiply(BigDecimal.valueOf(0.005));
        Account payMyBuddy = this.accountService.findByName("pay-my-buddy");
        payMyBuddy.setBalance(fee);
        this.accountService.save(payMyBuddy);
        transaction.setSender(this.accountService.getAccount());
        transaction.getSender().setBalance(transaction.getSender().getBalance().subtract(transaction.getAmount().add(fee)));
        this.accountService.save(transaction.getSender());
        transaction.setRecipient(this.accountService.findAccount(transaction.getRecipient().getIdentifier()));
        transaction.getRecipient().setBalance(transaction.getRecipient().getBalance().add(transaction.getAmount()));
        this.accountService.save(transaction.getRecipient());
        this.connectionService.create(payMyBuddy, transaction.getSender());
        this.connectionService.create(payMyBuddy, transaction.getRecipient());
        return repository.save(transaction);
    }

    public List<Transaction> findAllByAccountId(long l) throws ParameterNotProvidedException, AccountNotFoundException {
        Optional<Account> optionalAccount = this.accountRepository.findById(l);
        List<Transaction> transactionList = new ArrayList<>() {
        };
        if (optionalAccount.isPresent()) {
            transactionList = this.repository.findAllByAccountId(optionalAccount.get().getIdentifier());
        }
        return transactionList;
    }
}
