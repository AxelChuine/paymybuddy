package com.openclassrooms.paymybuddy.controller.impl;

import com.openclassrooms.paymybuddy.controller.IPersonController;
import com.openclassrooms.paymybuddy.service.IPersonService;
import org.springframework.ui.Model;

public class PersonControllerImpl implements IPersonController {

    private final IPersonService service;

    public PersonControllerImpl(IPersonService service) {
        this.service = service;
    }


    @Override
    public String findAll(Model model) {
        model.addAttribute("personList", this.service.findAll());
        return "people";
    }
}
