package com.rihavior.Bank_API_REST.repositories;

import com.rihavior.Bank_API_REST.entities.users.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThirdPartyRepository extends JpaRepository<ThirdParty, Long> {
}
