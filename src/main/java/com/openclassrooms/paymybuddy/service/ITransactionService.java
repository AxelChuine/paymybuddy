package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.service.dto.TransactionDTO;

import java.util.List;


public interface ITransactionService {
    List<TransactionDTO> findByAccountId();

    TransactionDTO sendMoneyToAFriendById(Integer senderId, Integer receiverId, Float money);
}
