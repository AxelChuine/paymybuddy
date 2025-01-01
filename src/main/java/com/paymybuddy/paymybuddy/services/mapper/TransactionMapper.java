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

    public TransactionDto toDto(Transaction transaction) {
        return new TransactionDto(
                transaction.getIdentifier(),
                transaction.getName(),
                transaction.getAmount(),
                Objects.nonNull(transaction.getSender()) ? accountMapper.toDto(transaction.getSender()) : null,
                Objects.nonNull(transaction.getReceiver()) ? accountMapper.toDto(transaction.getReceiver()) : null
        );
    }
}