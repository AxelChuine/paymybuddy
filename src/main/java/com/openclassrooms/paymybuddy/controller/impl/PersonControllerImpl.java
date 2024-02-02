package com.openclassrooms.paymybuddy.controller.impl;

import com.openclassrooms.paymybuddy.controller.IPersonController;
import com.openclassrooms.paymybuddy.service.IPersonService;
import com.openclassrooms.paymybuddy.service.dto.PersonDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PersonControllerImpl implements IPersonController {

    private final IPersonService service;

    public PersonControllerImpl(IPersonService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<List<PersonDto>> findAll() {
        return new ResponseEntity<List<PersonDto>>(this.service.findAll(), HttpStatus.OK);
    }
}
