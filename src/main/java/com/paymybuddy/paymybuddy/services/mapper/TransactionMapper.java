package com.paymybuddy.paymybuddy.services.mapper;

import com.paymybuddy.paymybuddy.dtos.TransactionDto;
import com.paymybuddy.paymybuddy.models.Transaction;
import org.springframework.stereotype.Service;

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
                Objects.nonNull(transaction.getSender()) ? accountMapper.toAccountVM(transaction.getSender()) : null,
                Objects.nonNull(transaction.getReceiver()) ? accountMapper.toAccountVM(transaction.getReceiver()) : null,
                transaction.getTransactionDate()
        );
    }

    public Transaction toModel(TransactionDto transactionDto) {
        return new Transaction(
                transactionDto.getIdentifier(),
                transactionDto.getName(),
                transactionDto.getAmount(),
                Objects.nonNull(transactionDto.getSender()) ? this.accountMapper.accountVMToModel(transactionDto.getSender()) : null,
                Objects.nonNull(transactionDto.getRecipient()) ? this.accountMapper.accountVMToModel(transactionDto.getRecipient()) : null,
                transactionDto.getTransactionDate()
        );
    }
}