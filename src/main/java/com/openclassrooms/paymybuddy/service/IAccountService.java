package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.service.dto.AccountDTO;

public interface IAccountService {

    AccountDTO createAnAccount(AccountDTO accountDTO);

    AccountDTO updateAccount(Float balance, Integer accountId);

    AccountDTO sendMoney(AccountDTO account, Float amount);
}
