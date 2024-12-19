package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.models.Connection;
import com.paymybuddy.paymybuddy.models.ConnectionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IConnectionRepository extends JpaRepository<Connection, ConnectionId> {
}
