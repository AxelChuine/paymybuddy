package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.service.dto.PersonDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/persons")
public interface IPersonController {

    @GetMapping("/all")
    ResponseEntity<List<PersonDto>> findAll();
}
