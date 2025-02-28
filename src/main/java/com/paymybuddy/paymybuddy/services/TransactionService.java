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
import java.time.LocalDateTime;
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

    public TransactionDto create(BigDecimal amount, String name, Long senderId, Long recipientId) throws ParameterNotProvidedException, AccountNotFoundException, AccountAlreadyExistsException {
        AccountDto account = this.accountService.findAccount(senderId);
        BigDecimal fee = amount.multiply(BigDecimal.valueOf(0.005));
        AccountDto payMyBuddy = this.accountService.findByName("pay-my-buddy");
        payMyBuddy.setBalance(fee);
        this.accountService.save(payMyBuddy);
        account.setBalance(account.getBalance().subtract(amount.add(fee)));
        this.accountService.updateAccount(account);
        AccountDto receiverDto = this.accountService.findAccount(recipientId);
        receiverDto.setBalance(receiverDto.getBalance().add(amount));
        this.accountService.updateAccount(receiverDto);
        Transaction transaction = new Transaction(
                name,
                amount,
                this.accountMapper.toModel(account),
                this.accountMapper.toModel(receiverDto),
                LocalDateTime.now());
        this.connectionService.create(payMyBuddy, account);
        this.connectionService.create(payMyBuddy, receiverDto);
        this.connectionService.create(account, receiverDto);
        return this.mapper.toTransactionDto(repository.save(transaction));
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
