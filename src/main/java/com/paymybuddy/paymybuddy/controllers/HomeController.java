package com.paymybuddy.paymybuddy.controllers;

import com.paymybuddy.paymybuddy.exceptions.AccountAlreadyExistsException;
import com.paymybuddy.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.paymybuddy.exceptions.ParameterNotProvidedException;
import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.services.AccountService;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping({"/home", "/"})
public class HomeController {
    private final AccountService accountService;

    public HomeController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping("/settings")
    public String findAccount(Model model) {
        model.addAttribute("account", this.accountService.getAccount());
        model.addAttribute("currentPage", "page2");
        return "home/settings";
    }

    @PostMapping("/settings")
    public String createAccount(Model model, @ModelAttribute Account account) throws AccountAlreadyExistsException, ParameterNotProvidedException, AccountNotFoundException {
        Account accountSaved = this.accountService.save(account);
        model.addAttribute("account", accountSaved);
        model.addAttribute("currentPage", "page2");
        return "home/settings";
    }

    @GetMapping({"/login", ""})
    public String logging(Model model) {
        model.addAttribute("account", new Account());
        return "home/login";
    }

    @PostMapping("/login")
    public String login(Model model, @ModelAttribute Account accountDto) throws BadRequestException {
        Account account = this.accountService.findByUsernameAndPassword(accountDto.getEmail(), accountDto.getPassword());
        if (Objects.isNull(account)) {
            model.addAttribute("error", "Le compte n'existe pas");
            return "home/login";
        }
        this.accountService.setAccount(account);
        return "redirect:/transaction/transaction";
    }

    @GetMapping("/new-account")
    public String newAccount(Model model) throws ParameterNotProvidedException {
        model.addAttribute("account", new Account());
        return "home/new-account";
    }

    @PostMapping("/new-account")
    public String newAccountCreated(@RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("password") String password, Model model) throws ParameterNotProvidedException, AccountNotFoundException {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            model.addAttribute("error", "Tous les champs sont requis.");
            return "home/new-account"; // Assurez-vous que cela renvoie à votre template
        }

        model.addAttribute("account", new Account());
        Account accountDto2 = this.accountService.createAccount(email, username, password);
        this.accountService.setAccount(accountDto2);
        return "redirect:/transaction/transaction";
    }
}
