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
        // FIXME: à enlever lorsque la couche sécurité sera mise en place.
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

    @PostMapping("/new-transaction")
    public String createTransaction(@ModelAttribute TransactionDto transactionDto, Model model) throws AccountAlreadyExistsException, ParameterNotProvidedException, AccountNotFoundException {
        TransactionDto transactionDto1 = this.service.create(transactionDto.getAmount(), transactionDto.getName(), 2L, transactionDto.getRecipient().getIdentifier());
        model.addAttribute("transaction", transactionDto1);
        return "transaction/transaction";
    }
}