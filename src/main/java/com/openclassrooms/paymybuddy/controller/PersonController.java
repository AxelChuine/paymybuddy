package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.service.IPersonService;
import com.openclassrooms.paymybuddy.service.dto.PersonDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/person")
public class PersonController {

    private final IPersonService service;

    public PersonController(IPersonService service) {
        this.service = service;
    }

    @RequestMapping(value = "/create-person", method = RequestMethod.GET)
    public String createPerson(Model model) {
        model.addAttribute("person", new PersonDto());
        return "person/create-person";
    }

    @RequestMapping(value = "/creation", method = RequestMethod.POST)
    public String submitCreatePerson (@ModelAttribute PersonDto personDto) {
        PersonDto personDto1 = this.service.createPerson(personDto);
        return "redirect:/account/create-account?person-id=" + personDto1.getIdentifier();
    }
}
