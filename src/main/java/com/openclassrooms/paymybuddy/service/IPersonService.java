package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.service.dto.PersonDto;

import java.util.List;

public interface IPersonService {
    List<PersonDto> findAll();

    PersonDto findByFullName(String firstName, String lastName);

    PersonDto updatePassword(PersonDto personDto, String newPassword);

    PersonDto createPerson(PersonDto personDto);

    PersonDto findById(Integer personId);
}
