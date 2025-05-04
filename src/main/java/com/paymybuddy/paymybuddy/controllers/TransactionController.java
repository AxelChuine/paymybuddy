package com.paymybuddy.paymybuddy.controllers;

import com.paymybuddy.paymybuddy.exceptions.AccountAlreadyExistsException;
import com.paymybuddy.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.paymybuddy.exceptions.ParameterNotProvidedException;
import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.models.Connection;
import com.paymybuddy.paymybuddy.models.Transaction;
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
    public String findAllTransactions(Model model) throws ParameterNotProvidedException, AccountNotFoundException {
        List<Connection> connections = new ArrayList<>(this.connectionService.findAllByAccount(this.accountService.getAccount()));
        List<Account> accountList = new ArrayList<>();
        /*for (Connection connection : connections) {
            accountList.add(this.accountService.findAccount(connection.getConnection().getIdentifier()));
        }*/
        List<Transaction> transactionDtoList = this.service.findAllByAccountId(this.accountService.getAccount().getIdentifier());
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("accountDtoList", accountList);
        model.addAttribute("transactions", transactionDtoList);
        model.addAttribute("currentPage", "page1");
        return "transaction/transaction";
    }

    @PostMapping("/transaction")
    public String createTransaction(@ModelAttribute Transaction transaction, Model model) throws AccountAlreadyExistsException, ParameterNotProvidedException, AccountNotFoundException {
        List<Connection> connectionList = this.connectionService.findAllByAccount(this.accountService.getAccount());
        List<Account> accountList = new ArrayList<>();
        for (Connection connection : connectionList) {
            accountList.add(this.accountService.findAccount(connection.getConnection().getIdentifier()));
        }
        List<Transaction> transactionList = this.service.findAllByAccountId(this.accountService.getAccount().getIdentifier());
        if (this.accountService.getAccount().getBalance().compareTo(transaction.getAmount()) < 0) {
            model.addAttribute("transaction", new Transaction());
            model.addAttribute("accountDtoList", accountList);
            model.addAttribute("error", "Vous ne pouvez pas faire cette transaction");
            model.addAttribute("transactions", transactionList);
            return "transaction/transaction";
        }
        Transaction transactionToReturn = this.service.create(transaction);
        transactionList.add(transactionToReturn);
        model.addAttribute("accountDtoList", accountList);
        model.addAttribute("transaction", transactionToReturn);
        model.addAttribute("transactions", transactionList);
        return "transaction/transaction";
    }
}
