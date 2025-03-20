package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dtos.AccountDto;
import com.paymybuddy.paymybuddy.exceptions.AccountAlreadyExistsException;
import com.paymybuddy.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.paymybuddy.exceptions.ParameterNotProvidedException;
import com.paymybuddy.paymybuddy.services.AccountService;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final AccountService accountService;

    public HomeController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping({"", "/"})
    public String index() {
        return "index";
    }

    @GetMapping("/settings")
    public String findAccount(Model model) {
        AccountDto accountDto = this.accountService.findById(3L);
        model.addAttribute("account", accountDto);
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

    @PostMapping("/transaction/transaction")
    public String newConnection(Model model, @ModelAttribute AccountDto accountDto) throws BadRequestException {
        AccountDto account = this.accountService.findByUsernameAndPassword(accountDto.getUsername(), accountDto.getPassword());
        if (Objects.nonNull(account.getIdentifier())) {
            this.accountService.setAccountDto(account);
        }
        model.addAttribute("account", account);
        return "/transaction/transaction";
    }
}
