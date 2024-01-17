package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.service.IPersonService;
import com.openclassrooms.paymybuddy.service.ITransactionService;
import org.springframework.stereotype.Controller;

@Controller
public class TransactionRest {

    private final ITransactionService service;

    public TransactionRest(ITransactionService service) {
        this.service = service;
    }
}
