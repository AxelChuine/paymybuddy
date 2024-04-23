package com.openclassrooms.paymybuddy.service.impl;

import com.openclassrooms.paymybuddy.model.Person;
import com.openclassrooms.paymybuddy.repository.IPersonRepository;
import com.openclassrooms.paymybuddy.service.dto.PersonDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {

    @Mock
    private IPersonRepository repository;

    @InjectMocks
    private PersonServiceImpl service;

    private Person person;

    private PersonDto personDto;

    @BeforeEach
    public void setPerson () {
        this.person = new Person(1, "Jean", "Dubois", "password", "test@gmail.com");
        this.personDto = new PersonDto(1, "Jean", "Dubois", "password", "test@gmail.com");
    }

    @Test
    public void findAllPersonsShouldReturnAListOfPersons () {
        List<Person> persons = new ArrayList<>();
        persons.add(this.person);
        PersonDto personDto = new PersonDto(this.person.getIdentifier(), person.getFirstName(), person.getLastName(), person.getPassword(), null);
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
        PersonDto personDto = new PersonDto(this.person.getIdentifier(), this.person.getFirstName(), this.person.getLastName(), this.person.getPassword(), null);

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

    @Test
    public void createPersonShouldCreateAPerson () {
        Mockito.when(this.repository.save(this.person)).thenReturn(this.person);
        PersonDto personDto1 = this.service.createPerson(this.personDto);

        assertEquals(personDto, personDto1);
    }
    
    @Test
    public void findPersonByIdShouldReturnAPerson () {
        PersonDto personDto = new PersonDto(1, "Jean", "Dubois", "password", null);

        when(this.repository.findById(personDto.getIdentifier())).thenReturn(Optional.ofNullable(this.person));
        PersonDto personToCompare = this.service.findById(personDto.getIdentifier());

        assertEquals(personDto, personToCompare);
    }

    @Test
    public void checkIfEmailExistsShouldReturnTrueIfEmailExists () {
        boolean exists = true;
        List<Person> persons = List.of(this.person);
        String email = "test@gmail.com";

        Mockito.when(this.repository.findAll()).thenReturn(persons);
        boolean emailExists = this.service.checkIfEmailExists(email);

        assertEquals(exists, emailExists);
    }

    @Test
    public void checkIfEmailExistsShouldReturnFalseIfEmailDoesNotExist () {
        boolean exists = false;
        List<Person> persons = List.of(this.person);
        String email = "test1@gmail.com";

        Mockito.when(this.repository.findAll()).thenReturn(persons);
        boolean emailExists = this.service.checkIfEmailExists(email);

        assertEquals(exists, emailExists);
    }

    @Test
    public void findPersonByEmailAndPasswordShouldReturnAPersonIfPersonExists () {
        String email = this.person.getEmail();
        String password = this.person.getPassword();

        Mockito.when(this.repository.findByEmailAndPassword(email, password)).thenReturn(this.person);
        PersonDto personDto1 = this.service.findByEmailAndPassword(email, password);

        assertEquals(personDto, personDto1);
    }

    @Test
    public void findPersonByEmailAndPasswordShouldReturnNullIfPersonDoesNotExist () {
        Mockito.when(this.repository.findByEmailAndPassword(this.person.getEmail(), this.person.getPassword())).thenReturn(null);
        PersonDto personDto1 = this.service.findByEmailAndPassword(this.person.getEmail(), this.person.getPassword());

        assertNull(personDto1);
    }

}
