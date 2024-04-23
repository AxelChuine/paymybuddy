package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonRepository extends JpaRepository<Person, Integer> {
    Person findByFirstNameAndLastName(String firstName, String lastName);
    Person findByEmailAndPassword(String email, String password);
}
