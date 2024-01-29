package com.openclassrooms.paymybuddy.service.mapper;

import com.openclassrooms.paymybuddy.model.Person;
import com.openclassrooms.paymybuddy.service.dto.PersonDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IPersonMapper {

    IPersonMapper INSTANCE = Mappers.getMapper(IPersonMapper.class);

    PersonDto personToPersonDto (Person person);

    List<PersonDto> personsToPersonsDto (List<Person> personList);

    Person personDtoToPerson (PersonDto personDto);

    List<Person> personsDtoToPersons (List<PersonDto> personDtos);
}
