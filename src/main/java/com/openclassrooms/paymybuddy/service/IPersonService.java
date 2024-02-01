package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.service.dto.PersonDto;

import java.util.List;


public interface IPersonService {
    List<PersonDto> findAll();
}
