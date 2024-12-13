package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
}
