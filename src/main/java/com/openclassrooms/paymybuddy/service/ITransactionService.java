package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.service.dto.TransactionDTO;

import java.util.List;


public interface ITransactionService {
    List<TransactionDTO> findAllByAccountId(Integer accountId);
}
