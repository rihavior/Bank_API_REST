package com.rihaviour.Bank_API_REST.repositories;

import com.rihaviour.Bank_API_REST.entities.accounts.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByOwnerUserName (String ownerUserName);
}
