package com.openclassrooms.paymybuddy.service.impl;

import com.openclassrooms.paymybuddy.repository.IPersonRepository;
import com.openclassrooms.paymybuddy.service.IPersonService;
import com.openclassrooms.paymybuddy.service.dto.PersonDto;
import com.openclassrooms.paymybuddy.service.mapper.IPersonMapper;

import java.util.List;

public class PersonServiceImpl implements IPersonService {


    private final IPersonRepository repository;

    private final IPersonMapper mapper;

    public PersonServiceImpl(IPersonRepository repository, IPersonMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<PersonDto> findAll() {
        return IPersonMapper.INSTANCE.personsToPersonsDto(this.repository.findAll());
    }

    @Override
    public PersonDto findByFullName(String firstName, String lastName) {
        return IPersonMapper.INSTANCE.personToPersonDto(this.repository.findByFullName(firstName, lastName));
    }
}
