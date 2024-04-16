package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.service.IAccountService;
import com.openclassrooms.paymybuddy.service.IPersonService;
import com.openclassrooms.paymybuddy.service.dto.AccountDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final IAccountService service;

    private final IPersonService personService;

    public AccountController(IAccountService service, IPersonService personService) {
        this.service = service;
        this.personService = personService;
    }

    @RequestMapping(value = "/create-account", method = RequestMethod.GET)
    public String createAccount(@ModelAttribute("person-id") Integer personId, Model model) {
        model.addAttribute("accounts", new AccountDTO());
        model.addAttribute("person-id", personId);
        return "account/create-account";
    }

    @RequestMapping(value = "/creation", method = RequestMethod.POST)
    public String createAccount(@ModelAttribute AccountDTO account, @ModelAttribute("person-id") Integer personId) {
        AccountDTO accountDTO = this.service.createAnAccount(account, personId);
        return "redirect:/transaction/new-transaction?account-id" + accountDTO.getIdentifier();
    }

}
