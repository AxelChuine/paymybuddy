package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.service.IPersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping
public class HomeController {

    private final IPersonService personService;

    public HomeController(IPersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String login(@ModelAttribute("email") String email, @ModelAttribute("password") String password, Model model) {
        return "login";
    }
}
