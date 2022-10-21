package com.rihavior.Bank_API_REST.repositories;

import com.rihavior.Bank_API_REST.entities.accounts.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByOwnerUserName (String ownerUserName);
}