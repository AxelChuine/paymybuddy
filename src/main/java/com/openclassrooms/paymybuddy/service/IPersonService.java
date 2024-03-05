package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.service.dto.PersonDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPersonService {
    List<PersonDto> findAll();

    PersonDto findByFullName(String firstName, String lastName);

    PersonDto updatePassword(PersonDto personDto, String newPassword);
}
