package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
    Account findByIdentifier(Long identifier);

    Account findByName(String accountName);

    Account findByEmail(String accountEmail);
}
