package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.service.ITransactionService;
import org.springframework.stereotype.Controller;

@Controller
public class TransactionControllerImpl {

    private final ITransactionService service;

    public TransactionControllerImpl(ITransactionService service) {
        this.service = service;
    }
}
