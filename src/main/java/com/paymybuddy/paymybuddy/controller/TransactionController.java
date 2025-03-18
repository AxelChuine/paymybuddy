package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dtos.AccountDto;
import com.paymybuddy.paymybuddy.dtos.ConnectionVM;
import com.paymybuddy.paymybuddy.dtos.TransactionDto;
import com.paymybuddy.paymybuddy.exceptions.AccountAlreadyExistsException;
import com.paymybuddy.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.paymybuddy.exceptions.ConnectionNotFoundException;
import com.paymybuddy.paymybuddy.exceptions.ParameterNotProvidedException;
import com.paymybuddy.paymybuddy.services.AccountService;
import com.paymybuddy.paymybuddy.services.ConnectionService;
import com.paymybuddy.paymybuddy.services.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService service;

    private final ConnectionService connectionService;

    private final AccountService accountService;

    public TransactionController(TransactionService service, ConnectionService connectionService, AccountService accountService) {
        this.service = service;
        this.connectionService = connectionService;
        this.accountService = accountService;
    }

    @GetMapping("/transaction")
    public String findAllTransactions(Model model) throws ParameterNotProvidedException, AccountNotFoundException, ConnectionNotFoundException {
        AccountDto accountDto = this.accountService.findById(2L);
        List<ConnectionVM> connections = this.connectionService.findAllByAccount(accountDto);
        List<AccountDto> accountDtoList = new ArrayList<>();
        for (ConnectionVM connection : connections) {
            accountDtoList.add(this.accountService.findAccount(connection.getConnectionId()));
        }
        List<TransactionDto> transactionDtoList = this.service.findAllByAccountId(2L);
        model.addAttribute("transaction", new TransactionDto());
        model.addAttribute("accountDtoList", accountDtoList);
        model.addAttribute("transactions", transactionDtoList);
        return "transaction/transaction";
    }

    @PostMapping("/transaction")
    public String createTransaction(@ModelAttribute TransactionDto transactionDto, Model model) throws AccountAlreadyExistsException, ParameterNotProvidedException, AccountNotFoundException {
        AccountDto accountDto = this.accountService.findById(2L);
        AccountDto recipient = this.accountService.findById(transactionDto.getRecipient().getIdentifier());
        transactionDto.setSender(accountDto);
        transactionDto.setRecipient(recipient);
        transactionDto.setAmount(transactionDto.getAmount());
        transactionDto.setRecipient(transactionDto.getRecipient());
        transactionDto.setName(transactionDto.getName());
        TransactionDto transactionToReturn = this.service.create(transactionDto);
        List<TransactionDto> transactionDtoList = this.service.findAllByAccountId(2L);
        model.addAttribute("transaction", transactionToReturn);
        model.addAttribute("transactions", transactionDtoList);
        return "transaction/transaction";
    }
}