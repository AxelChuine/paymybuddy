package com.openclassrooms.paymybuddy.service.impl;

import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.repository.ITransactionRepository;
import com.openclassrooms.paymybuddy.service.IAccountService;
import com.openclassrooms.paymybuddy.service.ITransactionService;
import com.openclassrooms.paymybuddy.service.dto.AccountDTO;
import com.openclassrooms.paymybuddy.service.dto.TransactionDTO;
import com.openclassrooms.paymybuddy.service.mapper.ITransactionMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public List<TransactionDTO> findByAccountId() {
        return ITransactionMapper.INSTANCE.transactionsToTransactionsDtos(this.repository.findAllByAccountId(1));
    }

    @Override
    public TransactionDTO sendMoneyToAFriendById(Integer senderId, Integer receiverId, Float money) {
        AccountDTO receiver = this.accountService.findById(receiverId);
        AccountDTO sender = this.accountService.findById(senderId);
        sender.setBalance(sender.getBalance() - money);
        LocalDateTime transactionDate = LocalDateTime.now();
        Transaction transaction = this.repository.save(new Transaction("transaction", new BigDecimal(money), senderId, receiverId, transactionDate));
        receiver.setBalance(receiver.getBalance() + money);
        this.accountService.save(receiver);
        this.accountService.save(sender);
        return ITransactionMapper.INSTANCE.transactionToTransactionDto(transaction);
    }
}
