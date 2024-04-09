package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.service.dto.AccountDTO;

import java.util.List;

public interface IAccountService {

    AccountDTO createAnAccount(AccountDTO accountDTO);

    AccountDTO updateAccount(Float balance, Integer accountId);

    AccountDTO addMoney(AccountDTO account, Float amount);

    List<AccountDTO> findAll();

    AccountDTO findById(Integer senderId);

    AccountDTO save(AccountDTO account);
}
