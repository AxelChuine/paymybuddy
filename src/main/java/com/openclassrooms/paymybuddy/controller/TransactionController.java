package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.service.ITransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("transaction")
public class TransactionController {

    private final ITransactionService service;

    public TransactionController(ITransactionService service) {
        this.service = service;
    }

    @GetMapping
    public String transaction(Model model) {
        model.addAttribute("transactions", this.service.findAllByAccountId(1));
        return "transaction/new-transaction";
    }
}
