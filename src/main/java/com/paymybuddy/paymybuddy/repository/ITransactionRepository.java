package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.sender.identifier = :id OR t.receiver.identifier = :id")
    List<Transaction> findAllByAccountId(@Param("id") Long id);
}
