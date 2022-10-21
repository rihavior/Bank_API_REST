package com.rihavior.Bank_API_REST.repositories;

import com.rihavior.Bank_API_REST.entities.accounts.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SavingsRepository extends JpaRepository<Savings, Long> {

    Optional<Savings> findByPrimaryOwnerUserName(String primaryOwnerUserName);

    Optional<Savings> findBySecondaryOwnerUserName(String secondaryOwnerUserName);

}
