package com.openclassrooms.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    @RequestMapping(value = "/new-transaction", method = RequestMethod.GET)
    public String newTransaction() {
        return "/transaction/new-transaction";
    }
}
