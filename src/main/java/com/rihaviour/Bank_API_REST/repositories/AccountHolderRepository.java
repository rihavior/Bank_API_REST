package com.rihaviour.Bank_API_REST.repositories;

import com.rihaviour.Bank_API_REST.entities.users.AccountHolder;
import com.rihaviour.Bank_API_REST.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {

    Optional<AccountHolder> findByUserName(String username);
}
