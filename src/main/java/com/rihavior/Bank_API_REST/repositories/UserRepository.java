package com.rihavior.Bank_API_REST.repositories;

import com.rihavior.Bank_API_REST.entities.users.AccountHolder;
import com.rihavior.Bank_API_REST.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AccountHolder, Long> {
    Optional<User> findByUsername(String username);
}
