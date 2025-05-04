package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
    Account findByIdentifier(Long identifier);

    Account findByName(String accountName);

    Optional<Account> findByEmail(String accountEmail);

    /*Optional<Account> findByEmailAndPassword(String username, String password);*/
    Account findByEmailAndPassword(String username, String password);

    List<Account> findAllByIdentifierIn(List<Long> connectionIds);
}
