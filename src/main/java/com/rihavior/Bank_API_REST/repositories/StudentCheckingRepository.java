package com.rihavior.Bank_API_REST.repositories;

import com.rihavior.Bank_API_REST.entities.accounts.StudentChecking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentCheckingRepository extends JpaRepository<StudentChecking, Long> {

    Optional<StudentChecking> findByPrimaryOwnerUsername(String primaryOwnerUserName);

    Optional<StudentChecking> findBySecondaryOwnerUsername(String secondaryOwnerUserName);


    Optional<StudentChecking> findByPrimaryOwnerId(Long primaryOwnerId);
}
