package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.service.IAccountService;
import com.openclassrooms.paymybuddy.service.dto.AccountDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
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
    public String findAllAccounts(Model model) {
        model.addAttribute("accounts", this.service.findAll());
        return "account/create-account";
    }

    @RequestMapping(value = "/creation", method = RequestMethod.POST)
    public String createAccount(@RequestBody AccountDTO account) {
        AccountDTO accountDTO = this.service.createAnAccount(account);
        return "redirect:/transaction/new-transaction";
    }

}
