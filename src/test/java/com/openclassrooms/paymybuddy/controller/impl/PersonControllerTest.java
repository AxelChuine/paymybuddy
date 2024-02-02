package com.openclassrooms.paymybuddy.controller.impl;

import com.openclassrooms.paymybuddy.service.dto.PersonDto;
import com.openclassrooms.paymybuddy.service.impl.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class PersonControllerTest {

    @Autowired
    private PersonControllerImpl controller;

    @InjectMocks
    private PersonServiceImpl service;

    private PersonDto personDto;

    @BeforeEach
    public void setUp () {
        this.personDto = new PersonDto(1, "Jean", "Dubois", "Password");
    }

    /*@Test
    public void findAllShouldReturnAListOfEntity () {
        List<PersonDto> personDtos = List.of(new PersonDto(), new PersonDto());

        when(this.service.findAll()).thenReturn(personDtos);
        ResponseEntity<List<PersonDto>> responseEntity = this.controller.findAll();

        assertEquals(personDtos, responseEntity.getBody());
    }*/
}
