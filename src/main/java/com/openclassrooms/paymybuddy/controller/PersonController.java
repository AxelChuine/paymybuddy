package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.service.IPersonService;
import com.openclassrooms.paymybuddy.service.dto.PersonDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/person")
public class PersonController {

    private final IPersonService service;

    public PersonController(IPersonService service) {
        this.service = service;
    }


    @GetMapping("/all")
    public String findAll(Model model) {
        model.addAttribute("personList", this.service.findAll());
        return "people";
    }

    @RequestMapping(value = "/creation", method = RequestMethod.POST)
    public String createPerson(Model model, @RequestBody PersonDto personDto) {
        PersonDto person = this.service.createPerson (personDto);
        return "createPerson";
    }
}
