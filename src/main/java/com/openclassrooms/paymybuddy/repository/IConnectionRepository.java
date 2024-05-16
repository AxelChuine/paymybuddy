package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.Connection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IConnectionRepository extends JpaRepository<Connection, Integer> {
}
