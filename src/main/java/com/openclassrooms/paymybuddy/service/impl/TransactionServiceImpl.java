package com.openclassrooms.paymybuddy.service.impl;

import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.repository.ITransactionRepository;
import com.openclassrooms.paymybuddy.service.IAccountService;
import com.openclassrooms.paymybuddy.service.ITransactionService;
import com.openclassrooms.paymybuddy.service.dto.AccountDTO;
import com.openclassrooms.paymybuddy.service.dto.TransactionDTO;
import com.openclassrooms.paymybuddy.service.mapper.ITransactionMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements ITransactionService {

    private final ITransactionRepository repository;

    private final IAccountService accountService;

    public TransactionServiceImpl(ITransactionRepository repository, IAccountService accountService) {
        this.repository = repository;
        this.accountService = accountService;
    }

    @Override
    public List<TransactionDTO> findAllByAccountId(Integer accountId) {
        return ITransactionMapper.INSTANCE.transactionsToTransactionsDtos(this.repository.findAllByAccountId(accountId));
    }

    @Override
    public TransactionDTO payMyBuddy(Integer pSender, String emailReceiver, Float amount) {
        AccountDTO sender = this.accountService.findById(pSender);
        AccountDTO receiver = this.accountService.findByEmail(emailReceiver);
        Float balanceSender = sender.getBalance() - amount;
        Float balanceReceiver = receiver.getBalance() + amount;
        sender.setBalance(balanceSender);
        receiver.setBalance(balanceReceiver);
        this.accountService.save(sender);
        this.accountService.save(receiver);
        return ITransactionMapper.INSTANCE.transactionToTransactionDto(this.repository.save(new Transaction(amount, sender.getIdentifier(), receiver.getIdentifier(), LocalDateTime.now())));
    }
}
