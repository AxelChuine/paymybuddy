package com.openclassrooms.paymybuddy.service.impl;

import com.openclassrooms.paymybuddy.repository.ITransactionRepository;
import com.openclassrooms.paymybuddy.service.ITransactionService;
import com.openclassrooms.paymybuddy.service.dto.TransactionDTO;
import com.openclassrooms.paymybuddy.service.mapper.ITransactionMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements ITransactionService {

    private final ITransactionRepository repository;

    public TransactionServiceImpl(ITransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TransactionDTO> findAllByAccountId(Integer accountId) {
        return ITransactionMapper.INSTANCE.transactionsToTransactionsDtos(this.repository.findAllByAccountId(accountId));
    }
}
