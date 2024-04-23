package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.service.IAccountService;
import com.openclassrooms.paymybuddy.service.dto.AccountDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final IAccountService service;


    public AccountController(IAccountService service) {
        this.service = service;
    }

    @RequestMapping(value = "/create-account", method = RequestMethod.GET)
    public String createAccount(Model model) {
        model.addAttribute("accounts", new AccountDTO());
        return "account/create-account";
    }

    @RequestMapping(value = "/creation", method = RequestMethod.POST)
    public String createAccount(@ModelAttribute AccountDTO account) {
        AccountDTO accountDTO = this.service.createAnAccount(account);
        return "redirect:/home?account-id=" + accountDTO.getIdentifier();
    }

}
