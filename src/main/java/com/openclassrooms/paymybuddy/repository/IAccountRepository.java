package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Integer> {
    Account findByEmailAndPassword(String email, String password);

    List<Account> findAllConnectionsByIdentifier(Integer identifier);

    Account findByEmail(String email);
}
