package com.openclassrooms.paymybuddy.service.impl;

import com.openclassrooms.paymybuddy.repository.ITransactionRepository;
import com.openclassrooms.paymybuddy.service.ITransactionService;
import com.openclassrooms.paymybuddy.service.dto.TransactionDTO;
import com.openclassrooms.paymybuddy.service.mapper.ITransactionMapper;

import java.util.List;

public class TransactionServiceImpl implements ITransactionService {

    private final ITransactionRepository repository;

    public TransactionServiceImpl(ITransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TransactionDTO> findAllByAccountId() {
        return ITransactionMapper.INSTANCE.transactionsToTransactionsDtos(this.repository.findAllByAccountId(1));
    }
}
