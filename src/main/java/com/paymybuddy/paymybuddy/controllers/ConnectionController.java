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
            Account connection = this.accountService.findByEmail(account.getEmail());
            this.accountService.getAccount().addConnection(connection);
            this.accountService.save(currentAccount);
            this.service.create(currentAccount, connection);
            this.service.create(connection, currentAccount);
            model.addAttribute("success", "Connexion créée avec succès");
            return "redirect:/transaction/transaction";

        } catch (AccountNotFoundException e) {
            model.addAttribute("currentPage", "page3");
            model.addAttribute("error", "Le compte n'existe pas");
            model.addAttribute("account", new Account());
            return "connection/connection";
        } catch (IllegalArgumentException e) {
            model.addAttribute("currentPage", "page3");
            model.addAttribute("error", "La connexion existe déjà");
            model.addAttribute("account", new Account());
            return "connection/connection";
        }
    }
}
