package com.paymybuddy.paymybuddy.controllers;

import com.paymybuddy.paymybuddy.dtos.AccountDto;
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
import java.util.Optional;

import static org.springframework.data.repository.util.ClassUtils.ifPresent;

@Controller
@RequestMapping({"/home", "/"})
public class HomeController {
    private final AccountService accountService;

    public HomeController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping("/settings")
    public String findAccount(Model model) {
        model.addAttribute("account", this.accountService.getAccountDto());
        model.addAttribute("currentPage", "page2");
        return "home/settings";
    }

    @PostMapping("/settings")
    public String createAccount(Model model, @ModelAttribute AccountDto accountDto) throws AccountAlreadyExistsException, ParameterNotProvidedException, AccountNotFoundException {
        AccountDto account = this.accountService.save(accountDto);
        model.addAttribute("account", account);
        model.addAttribute("currentPage", "page2");
        return "home/settings";
    }

    @GetMapping({"/login", ""})
    public String logging(Model model) {
        model.addAttribute("account", new AccountDto());
        return "home/login";
    }

    @PostMapping("/login")
    public String login(Model model, @ModelAttribute AccountDto accountDto) throws BadRequestException {
        AccountDto account = this.accountService.findByUsernameAndPassword(accountDto.getEmail(), accountDto.getPassword());
        if (Objects.isNull(account)) {
            model.addAttribute("error", "Le compte n'existe pas");
            return "home/login";
        }
        this.accountService.setAccountDto(account);
        return "redirect:/transaction/transaction";
    }

    @GetMapping("/new-account")
    public String newAccount(Model model) throws ParameterNotProvidedException {
        model.addAttribute("account", new AccountDto());
        return "home/new-account";
    }

    @PostMapping("/new-account")
    public String newAccountCreated(@RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("password") String password, Model model) throws ParameterNotProvidedException, AccountNotFoundException {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            model.addAttribute("error", "Tous les champs sont requis.");
            return "home/new-account"; // Assurez-vous que cela renvoie à votre template
        }

        model.addAttribute("account", new AccountDto());
        AccountDto accountDto2 = this.accountService.createAccount(email, username, password);
        this.accountService.setAccountDto(accountDto2);
        return "redirect:/transaction/transaction";
        /*try {
            AccountDto accountDto2 = this.accountService.createAccount(email, username, password);
            this.accountService.setAccountDto(accountDto2);
            return "redirect:/transaction/transaction";
        } catch (Exception) {
            BadRequestException badRequestException = new BadRequestException("Account already exists");
            model.addAttribute("exception", badRequestException);
            return "error/403";
        }*/
    }
}
