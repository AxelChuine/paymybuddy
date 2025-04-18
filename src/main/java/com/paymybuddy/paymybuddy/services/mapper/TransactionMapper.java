package com.paymybuddy.paymybuddy.services.mapper;

import com.paymybuddy.paymybuddy.dtos.TransactionDto;
import com.paymybuddy.paymybuddy.models.Transaction;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TransactionMapper {
    private final AccountMapper accountMapper;

    public TransactionMapper(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    public TransactionDto toTransactionDto(Transaction transaction) {
        return new TransactionDto(
                transaction.getIdentifier(),
                transaction.getName(),
                transaction.getAmount(),
                Objects.nonNull(transaction.getSender()) ? accountMapper.toAccountDto(transaction.getSender()) : null,
                Objects.nonNull(transaction.getReceiver()) ? accountMapper.toAccountDto(transaction.getReceiver()) : null,
                transaction.getTransactionDate()
        );
    }

    public List<TransactionDto> toTransactionDtoList(List<Transaction> transactionList) {
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        for (Transaction transaction : transactionList) {
            transactionDtoList.add(toTransactionDto(transaction));
        }
        return transactionDtoList;
    }

    public Transaction toModel(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setIdentifier(Objects.nonNull(transactionDto.getIdentifier()) ? transactionDto.getIdentifier() : null);
        transaction.setName(transactionDto.getName());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setSender(Objects.nonNull(transactionDto.getSender()) ? this.accountMapper.toModel(transactionDto.getSender()) : null);
        transaction.setReceiver(Objects.nonNull(transactionDto.getRecipient()) ? this.accountMapper.toModel(transactionDto.getRecipient()) : null);
        transaction.setTransactionDate(Objects.nonNull(transactionDto.getTransactionDate()) ? transactionDto.getTransactionDate() : LocalDateTime.now());
        return transaction;
    }
}