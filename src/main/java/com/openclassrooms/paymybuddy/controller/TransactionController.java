package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.service.ITransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
    private final ITransactionService service;

    public TransactionController(ITransactionService service) {
        this.service = service;
    }

    @RequestMapping(value = "/new-transaction", method = RequestMethod.GET)
    public String newTransaction(@ModelAttribute("account-id") Integer accountId, Model model) {
        model.addAttribute("new transaction", new Transaction());
        return "transfer";
    }

    @RequestMapping(value = "/new-transaction-success", method = RequestMethod.POST)
    public String newTransactionSuccess(@ModelAttribute("account-id") Integer accountId, @ModelAttribute("amount")BigDecimal amount, Model model) {
        return "new-transaction-success";
    }
}