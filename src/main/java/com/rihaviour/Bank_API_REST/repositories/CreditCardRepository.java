package com.rihaviour.Bank_API_REST.repositories;

import com.rihaviour.Bank_API_REST.entities.accounts.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

    Optional<CreditCard> findByPrimaryOwnerUserName(String primaryOwnerUserName);

    Optional<CreditCard> findBySecondaryOwnerUserName(String secondaryOwnerUserName);

}
