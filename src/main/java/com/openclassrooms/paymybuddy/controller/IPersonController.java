package com.openclassrooms.paymybuddy.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/person")
public interface IPersonController {

    @GetMapping("/all")
    String findAll(Model model);
}
