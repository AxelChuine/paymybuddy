package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.dtos.AccountVM;
import com.paymybuddy.paymybuddy.dtos.TransactionDto;
import com.paymybuddy.paymybuddy.exceptions.AccountAlreadyExistsException;
import com.paymybuddy.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.paymybuddy.exceptions.ParameterNotProvidedException;
import com.paymybuddy.paymybuddy.models.Transaction;
import com.paymybuddy.paymybuddy.repository.ITransactionRepository;
import com.paymybuddy.paymybuddy.services.mapper.AccountMapper;
import com.paymybuddy.paymybuddy.services.mapper.TransactionMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class TransactionService {
    private final ITransactionRepository repository;

    private final AccountService accountService;

    private final TransactionMapper mapper;

    private final AccountMapper accountMapper;

    private final ConnectionService connectionService;

    public TransactionService(ITransactionRepository repository, AccountService accountService, TransactionMapper mapper, AccountMapper accountMapper, ConnectionService connectionService) {
        this.repository = repository;
        this.accountService = accountService;
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
        AccountVM senderVM = this.accountService.findAccount(senderId);
        BigDecimal fee = amount.multiply(BigDecimal.valueOf(0.005));
        AccountVM payMyBuddy = this.accountService.findAccount(recipientId);
        payMyBuddy.setBalance(fee);
        this.accountService.save(payMyBuddy);
        senderVM.setBalance(senderVM.getBalance().subtract(amount.add(fee)));
        this.accountService.updateAccount(senderVM);
        AccountVM receiverVM = this.accountService.findAccount(recipientId);
        receiverVM.setBalance(receiverVM.getBalance().add(amount));
        this.accountService.updateAccount(receiverVM);
        Transaction transaction = new Transaction(
                amount,
                name,
                this.accountMapper.accountVMToModel(senderVM),
                this.accountMapper.accountVMToModel(receiverVM),
                LocalDateTime.now());
        this.connectionService.create(payMyBuddy, senderVM);
        this.connectionService.create(payMyBuddy, receiverVM);
        this.connectionService.create(senderVM, receiverVM);
        return this.mapper.toTransactionDto(repository.save(transaction));
    }
}
