package com.paymybuddy.paymybuddy.controllers;

import com.paymybuddy.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.paymybuddy.exceptions.ParameterNotProvidedException;
import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.services.AccountService;
import com.paymybuddy.paymybuddy.services.ConnectionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller
@RequestMapping("/connection")
public class ConnectionController {
    private final ConnectionService service;

    private final AccountService accountService;

    public ConnectionController(ConnectionService service, AccountService accountService) {
        this.service = service;
        this.accountService = accountService;
    }

    @GetMapping("/connection")
    public String connection(Model model) throws ParameterNotProvidedException, AccountNotFoundException {
        model.addAttribute("account", new Account());
        model.addAttribute("currentPage", "page3");
        return "connection/connection";
    }

    @PostMapping("/connection")
    public String createConnection(@ModelAttribute Account account, Model model) throws ParameterNotProvidedException, AccountNotFoundException {
        try {
            Account currentAccount = this.accountService.getAccount();
            model.addAttribute("account", new Account());
            model.addAttribute("currentPage", "page3");
            if (currentAccount == null) {
                model.addAttribute("error", "Vous n'êtes pas connecté");
                return "connection/connection";
            }
            Account connectionToAdd = this.accountService.findAccount(account.getIdentifier());
            if (!Objects.isNull(connectionToAdd)) {
                this.accountService.getAccount().addConnection(connectionToAdd);
            }
            model.addAttribute("error", "Impossible de créer la connexion");
            return "connection/connection";

        } catch (AccountNotFoundException e) {
            model.addAttribute("currentPage", "page3");
            model.addAttribute("error", "Le compte n'existe pas");
            model.addAttribute("account", new Account());
            return "connection/connection";
        }
    }
}
