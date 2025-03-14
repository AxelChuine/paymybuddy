package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dtos.AccountDto;
import com.paymybuddy.paymybuddy.dtos.AccountVM;
import com.paymybuddy.paymybuddy.dtos.ConnectionDto;
import com.paymybuddy.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.paymybuddy.exceptions.ParameterNotProvidedException;
import com.paymybuddy.paymybuddy.services.AccountService;
import com.paymybuddy.paymybuddy.services.ConnectionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/connection")
public class ConnectionController {
    private final ConnectionService service;

    private final AccountService accountService;

    private List<AccountVM> connections = new ArrayList<>();

    public ConnectionController(ConnectionService service, AccountService accountService) {
        this.service = service;
        this.accountService = accountService;
    }

    @GetMapping("/connection")
    public String connection(Model model) throws ParameterNotProvidedException, AccountNotFoundException {
        List<AccountDto> accounts = this.accountService.findAllDto();
        model.addAttribute("account", new AccountDto());
        model.addAttribute("accounts", accounts);
        return "/connection/connection";
    }

    @PostMapping("/new-connection")
    public String createConnection(Model model, @ModelAttribute AccountDto accountDto) throws ParameterNotProvidedException, AccountNotFoundException {
        ConnectionDto connection = this.service.create(2L, accountDto.getEmail());
        model.addAttribute("connection", connection);
        return "/connection/new-connection";
    }
}