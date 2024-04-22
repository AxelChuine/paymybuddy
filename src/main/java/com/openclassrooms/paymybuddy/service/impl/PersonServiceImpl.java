package com.openclassrooms.paymybuddy.service.impl;

import com.openclassrooms.paymybuddy.model.Person;
import com.openclassrooms.paymybuddy.repository.IPersonRepository;
import com.openclassrooms.paymybuddy.service.IPersonService;
import com.openclassrooms.paymybuddy.service.dto.PersonDto;
import com.openclassrooms.paymybuddy.service.mapper.IPersonMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PersonServiceImpl implements IPersonService {


    private final IPersonRepository repository;


    public PersonServiceImpl(IPersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PersonDto> findAll() {
        return IPersonMapper.INSTANCE.personsToPersonsDto(this.repository.findAll());
    }

    @Override
    public PersonDto findByFullName(String firstName, String lastName) {
        return IPersonMapper.INSTANCE.personToPersonDto(this.repository.findByFirstNameAndLastName(firstName, lastName));
    }

    @Override
    public PersonDto updatePassword(PersonDto personDto, String newPassword) {
        PersonDto personDto1 = this.findByFullName(personDto.getFirstName(), personDto.getLastName());
        personDto1.setPassword(newPassword);
        this.repository.save(IPersonMapper.INSTANCE.personDtoToPerson(personDto1));
        return personDto1;
    }

    @Override
    public PersonDto createPerson(PersonDto personDto) {
        Person person = null;
        if(!checkIfEmailExists(personDto.getEmail())) {
            person = this.repository.save(IPersonMapper.INSTANCE.personDtoToPerson(personDto));
        }
        return IPersonMapper.INSTANCE.personToPersonDto(person);
    }

    @Override
    public PersonDto findById(Integer personId) {
        Optional<Person> person = this.repository.findById(personId);
        return person.map(IPersonMapper.INSTANCE::personToPersonDto).orElse(null);
    }

    @Override
    public boolean checkIfEmailExists(String email) {
        Set<Person> persons = new HashSet<>(this.repository.findAll());
        return persons.stream().anyMatch(person -> Objects.equals(person.getEmail(), email));
    }
}
