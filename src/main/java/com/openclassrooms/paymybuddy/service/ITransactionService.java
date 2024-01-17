package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.service.dto.TransactionDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ITransactionService {
    List<TransactionDTO> findAllByAccountId();
}
