package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dtos.TransactionDto;
import com.paymybuddy.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.paymybuddy.exceptions.ParameterNotProvidedException;
import com.paymybuddy.paymybuddy.services.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping("/transaction")
    public String findAllTransactions(Model model) throws ParameterNotProvidedException, AccountNotFoundException {
        List<TransactionDto> transactionDtoList = this.service.findAllByAccountId(2L);
        model.addAttribute("transactions", transactionDtoList);
        return "transaction/transaction";
    }
}