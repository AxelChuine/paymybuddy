package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.service.IAccountService;
import com.openclassrooms.paymybuddy.service.dto.AccountDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping
public class HomeController {

    private final IAccountService accountService;

    public HomeController(IAccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String enterLogin(Model model) {
        return "login";
    }

    @RequestMapping(value = "logged-in", method = RequestMethod.POST)
    public String login(@ModelAttribute("email") String email, @ModelAttribute("password") String password, Model model) {
        AccountDTO accountDTO = this.accountService.findAccountByEmailAndPassword(email, password);
        return "redirect:/home";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(@ModelAttribute("account-id") Integer accountId, Model model) {
        return "home";
    }
}
