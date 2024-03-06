package com.openclassrooms.paymybuddy.service.impl;

import com.openclassrooms.paymybuddy.model.Person;
import com.openclassrooms.paymybuddy.repository.IPersonRepository;
import com.openclassrooms.paymybuddy.service.dto.PersonDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {

    @Mock
    private IPersonRepository repository;

    @InjectMocks
    private PersonServiceImpl service;

    private Person person;

    @BeforeEach
    public void setPerson () {
        this.person = new Person(1, "Jean", "Dubois", "password");
    }

    @Test
    public void findAllPersonsShouldReturnAListOfPersons () {
        List<Person> persons = new ArrayList<>();
        persons.add(this.person);
        PersonDto personDto = new PersonDto(this.person.getIdentifier(), person.getFirstName(), person.getLastName(), person.getPassword());
        List<PersonDto> personDtos = new ArrayList<>();
        personDtos.add(personDto);

        when(this.repository.findAll()).thenReturn(persons);
        List<PersonDto> personsDtosToCompare = this.service.findAll();

        assertEquals(personDtos, personsDtosToCompare);
    }

    @Test
    public void findPersonByFullNameShouldReturnOneEntity () {
        String firstName = "Jean";
        String lastName = "Dubois";
        PersonDto personDto = new PersonDto(this.person.getIdentifier(), this.person.getFirstName(), this.person.getLastName(), this.person.getPassword());

        when(this.repository.findByFirstNameAndLastName(firstName, lastName)).thenReturn(this.person);
        PersonDto personDtoToCompare = this.service.findByFullName (firstName, lastName);

        assertEquals(personDto, personDtoToCompare);
    }

    @Test
    public void updatePasswordShouldChangeThePasswordOfThePerson () {
        String newPassword = "bo";
        PersonDto personDto = new PersonDto();
        personDto.setIdentifier(1);
        personDto.setFirstName(this.person.getFirstName());
        personDto.setLastName(this.person.getLastName());
        personDto.setPassword(newPassword);

        when(this.repository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName())).thenReturn(this.person);
        PersonDto personDtoToCompare = this.service.updatePassword (personDto, newPassword);

        assertEquals(personDto, personDtoToCompare);
    }
    

    

}
