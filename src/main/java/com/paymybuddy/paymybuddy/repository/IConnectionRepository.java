package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.models.Account;
import com.paymybuddy.paymybuddy.models.Connection;
import com.paymybuddy.paymybuddy.models.ConnectionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IConnectionRepository extends JpaRepository<Connection, ConnectionId> {
    Connection findByConnection(Account account);

    List<Connection> findAllByAccount(Account account);

    List<Connection> findAllByConnection(Account account);
}
