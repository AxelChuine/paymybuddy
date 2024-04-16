package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.service.dto.AccountDTO;

import java.util.List;

public interface IAccountService {

    AccountDTO createAnAccount(AccountDTO accountDTO, Integer personId);

    AccountDTO updateAccount(Float balance, Integer accountId);

    AccountDTO sendMoney(AccountDTO account, Float amount);

    List<AccountDTO> findAll();
}
