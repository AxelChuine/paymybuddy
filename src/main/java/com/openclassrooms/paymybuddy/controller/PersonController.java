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


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String findAll(Model model) {
        model.addAttribute("personList", this.service.findAll());
        return "people";
    }

    @RequestMapping(value = "/create-person", method = RequestMethod.GET)
    public String createPerson(Model model) {
        model.addAttribute("person", new PersonDto());
        return "person/create-person";
    }

    @RequestMapping(value = "/creation", method = RequestMethod.POST)
    public String submitCreatePerson (@RequestBody PersonDto personDto) {
        PersonDto personDto1 = this.service.createPerson(personDto);
        return "redirect:home";
    }
}
