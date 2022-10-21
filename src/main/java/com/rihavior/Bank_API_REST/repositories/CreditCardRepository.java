package com.rihavior.Bank_API_REST.repositories;

import com.rihavior.Bank_API_REST.entities.accounts.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

    Optional<CreditCard> findByPrimaryOwnerUsername(String primaryOwnerUserName);

    Optional<CreditCard> findBySecondaryOwnerUsername(String secondaryOwnerUserName);

}
