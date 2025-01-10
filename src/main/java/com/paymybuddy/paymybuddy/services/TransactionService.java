package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.dtos.AccountVM;
import com.paymybuddy.paymybuddy.dtos.TransactionDto;
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

    public TransactionService(ITransactionRepository repository, AccountService accountService, TransactionMapper mapper, AccountMapper accountMapper) {
        this.repository = repository;
        this.accountService = accountService;
        this.mapper = mapper;
        this.accountMapper = accountMapper;
    }

    /**
     * Service où se trouve les méthodes de paiement:
     * échange d'argent entre le compte A et le compte B et création de l'objet en base de données
     */

    public TransactionDto create(BigDecimal amount, String name, AccountVM sender, AccountVM receiver) {
        Transaction transaction = new Transaction(
                amount,
                name,
                Objects.nonNull(sender) ? this.accountMapper.accountVMToModel(sender) : null,
                Objects.nonNull(receiver) ? this.accountMapper.accountVMToModel(receiver) : null,
                LocalDateTime.now());
        return this.mapper.toTransactionDto(repository.save(transaction));
    }

    public TransactionDto payABuddy(BigDecimal amount, String name, Long senderId, Long recipientId) throws ParameterNotProvidedException, AccountNotFoundException {
        AccountVM senderVM = this.accountService.findAccount(senderId);
        senderVM.setBalance(senderVM.getBalance().subtract(amount));
        this.accountService.updateAccount(senderVM);
        AccountVM receiverVM = this.accountService.findAccount(recipientId);
        receiverVM.setBalance(receiverVM.getBalance().add(amount));
        this.accountService.updateAccount(receiverVM);
        return this.create(amount, name, senderVM, receiverVM);
    }
}
