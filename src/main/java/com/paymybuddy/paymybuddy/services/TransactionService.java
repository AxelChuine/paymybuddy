package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.dtos.AccountDto;
import com.paymybuddy.paymybuddy.dtos.TransactionDto;
import com.paymybuddy.paymybuddy.exceptions.AccountAlreadyExistsException;
import com.paymybuddy.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.paymybuddy.exceptions.ParameterNotProvidedException;
import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.models.Transaction;
import com.paymybuddy.paymybuddy.repository.IAccountRepository;
import com.paymybuddy.paymybuddy.repository.ITransactionRepository;
import com.paymybuddy.paymybuddy.services.mapper.AccountMapper;
import com.paymybuddy.paymybuddy.services.mapper.TransactionMapper;
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

    private final TransactionMapper mapper;

    private final AccountMapper accountMapper;

    private final ConnectionService connectionService;

    public TransactionService(ITransactionRepository repository, AccountService accountService, IAccountRepository accountRepository, TransactionMapper mapper, AccountMapper accountMapper, ConnectionService connectionService) {
        this.repository = repository;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.mapper = mapper;
        this.accountMapper = accountMapper;
        this.connectionService = connectionService;
    }

    /**
     * Service où se trouve la méthodes de paiement:
     * échange d'argent entre le compte A et le compte B et création de l'objet en base de données
     * les frais de gestion sont payés
     */

    public TransactionDto create(final TransactionDto transactionDto) throws ParameterNotProvidedException, AccountNotFoundException, AccountAlreadyExistsException {
        BigDecimal fee = transactionDto.getAmount().multiply(BigDecimal.valueOf(0.005));
        AccountDto payMyBuddy = this.accountService.findByName("pay-my-buddy");
        payMyBuddy.setBalance(fee);
        this.accountService.save(payMyBuddy);
        transactionDto.getSender().setBalance(transactionDto.getSender().getBalance().subtract(transactionDto.getAmount().add(fee)));
        this.accountService.updateAccount(transactionDto.getSender());
        transactionDto.getRecipient().setBalance(transactionDto.getRecipient().getBalance().add(transactionDto.getAmount()));
        this.accountService.updateAccount(transactionDto.getRecipient());
        this.connectionService.create(payMyBuddy, transactionDto.getSender());
        this.connectionService.create(payMyBuddy, transactionDto.getRecipient());
        this.connectionService.create(transactionDto.getSender(), transactionDto.getRecipient());
        return this.mapper.toTransactionDto(repository.save(this.mapper.transactionDtoToTransaction(transactionDto)));
    }

    public List<TransactionDto> findAllByAccountId(long l) throws ParameterNotProvidedException, AccountNotFoundException {
        Optional<Account> optionalAccount = this.accountRepository.findById(l);
        List<Transaction> transactionList = new ArrayList<>() {
        };
        if (optionalAccount.isPresent()) {
            transactionList = this.repository.findAllByAccountId(optionalAccount.get().getIdentifier());
        }
        return this.mapper.toTransactionDtoList(transactionList);
    }
}
