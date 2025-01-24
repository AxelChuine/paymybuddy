package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.dtos.ConnectionVM;
import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.models.Connection;
import com.paymybuddy.paymybuddy.models.ConnectionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IConnectionRepository extends JpaRepository<Connection, ConnectionId> {
    Connection findByConnection(Account account);

    List<Connection> findAllByAccount(Account account);
}
