package com.rihaviour.Bank_API_REST.repositories;

import com.rihaviour.Bank_API_REST.entities.accounts.Account;
import com.rihaviour.Bank_API_REST.entities.accounts.Checking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CheckingRepository extends JpaRepository<Account, Long> {

    Optional<Checking> findByPrimaryOwnerUserName(String primaryOwnerUserName);

    Optional<Checking> findBySecondaryOwnerUserName(String primaryOwnerUserName);

}
