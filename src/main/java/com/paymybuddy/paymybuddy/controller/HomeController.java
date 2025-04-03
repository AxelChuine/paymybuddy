package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dtos.AccountDto;
import com.paymybuddy.paymybuddy.exceptions.AccountAlreadyExistsException;
import com.paymybuddy.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.paymybuddy.exceptions.ParameterNotProvidedException;
import com.paymybuddy.paymybuddy.services.AccountService;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final AccountService accountService;

    public HomeController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping("/settings")
    public String findAccount(Model model) {
        AccountDto accountDto = this.accountService.findById(3L);
        model.addAttribute("account", accountDto);
        model.addAttribute("currentPage", "page2");
        return "/home/settings";
    }

    @PostMapping("/settings")
    public String createAccount(Model model, @ModelAttribute AccountDto accountDto) throws AccountAlreadyExistsException, ParameterNotProvidedException, AccountNotFoundException {
        AccountDto account = accountDto;
        account.setIdentifier(3L);
        account = this.accountService.save(accountDto);
        model.addAttribute("account", account);
        return "/home/settings";
    }

    @GetMapping("/login")
    public String logging(Model model) {
        model.addAttribute("account", new AccountDto());
        return "/home/login";
    }

    @PostMapping("/login")
    public String login(Model model, @ModelAttribute AccountDto accountDto) throws BadRequestException {
        AccountDto account = this.accountService.findByUsernameAndPassword(accountDto.getEmail(), accountDto.getPassword());
        if (Objects.isNull(account.getIdentifier())) {
            model.addAttribute("error", "Le compte n'existe pas");
            return "/home/login";
        }
        this.accountService.setAccountDto(account);
        model.addAttribute("account", account);
        return "redirect:/transaction/transaction";
    }

    @PostMapping("/transaction/transaction")
    public String newConnection(Model model, @ModelAttribute AccountDto accountDto) throws BadRequestException {
        AccountDto account = this.accountService.findByUsernameAndPassword(accountDto.getUsername(), accountDto.getPassword());
        if (Objects.nonNull(account.getIdentifier())) {
            this.accountService.setAccountDto(account);
        }
        model.addAttribute("account", account);
        return "/transaction/transaction";
    }

    @GetMapping("/new-account")
    public String newAccount(Model model) throws ParameterNotProvidedException {
        model.addAttribute("account", new AccountDto());
        return "/home/new-account";
    }

    @PostMapping("/new-account")
    public String newAccountCreated(@RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("password") String password, Model model) throws ParameterNotProvidedException, AccountNotFoundException {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            model.addAttribute("error", "Tous les champs sont requis.");
            return "/home/new-account"; // Assurez-vous que cela renvoie à votre template
        }

        model.addAttribute("account", new AccountDto());
        Boolean accountExists = this.accountService.checkIfExists(email);
        if (Objects.equals(accountExists, false)) {
            AccountDto accountDto2 = this.accountService.createAccount(email, username, password);
            this.accountService.setAccountDto(accountDto2);
            return "redirect:/transaction/transaction";
        } else {
            BadRequestException badRequestException = new BadRequestException("Account already exists");
            model.addAttribute("exception", badRequestException);
            return "/error/403";
        }
    }


    /*@PostMapping("/home/new-account")
    public String newAccountCreated(@RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("password") String password, Model model) throws BadRequestException, ParameterNotProvidedException, AccountNotFoundException {
        AccountDto accountDto1 = this.accountService.findByEmail(email);
        if (Objects.nonNull(accountDto1.getIdentifier())) {
            BadRequestException badRequestException = new BadRequestException("Account already exists");
            model.addAttribute("exception", badRequestException);
            return "/error/403";
        } else {
            AccountDto accountDto2 = this.accountService.createAccount(email, username, password);
            this.accountService.setAccountDto(accountDto2);
            return "redirect:/transaction/transaction";
        }
    }*/
}
