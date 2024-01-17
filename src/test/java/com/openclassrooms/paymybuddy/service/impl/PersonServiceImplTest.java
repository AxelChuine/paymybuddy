package com.openclassrooms.paymybuddy.service.impl;

import com.openclassrooms.paymybuddy.model.Person;
import com.openclassrooms.paymybuddy.repository.IPersonRepository;
import com.openclassrooms.paymybuddy.service.IPersonService;
import com.openclassrooms.paymybuddy.service.dto.PersonDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PersonServiceImplTest {

    @MockBean
    private IPersonRepository repository;

    @Autowired
    private IPersonService service;

    @BeforeEach
    public void setPerson () {
        Person person = new Person(1, "Jean", "Dubois", "password");
    }

    @Test
    public void findAllPersonsShouldReturnAListOfPersons () {
        List<Person> personDtos = List.of(new Person(), new Person());

        when(this.repository.findAll()).thenReturn(personDtos);
        List<PersonDto> personsDtosToCompare = this.service.findAll();

        assertEquals(personDtos, personsDtosToCompare);
    }
}
