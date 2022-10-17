package com.rihaviour.Bank_API_REST.repositories;

import com.rihaviour.Bank_API_REST.entities.accounts.Checking;
import com.rihaviour.Bank_API_REST.entities.accounts.StudentChecking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentCheckingRepository extends JpaRepository<StudentChecking, Long> {

    Optional<StudentChecking> findByPrimaryOwnerUserName(String primaryOwnerUserName);

    Optional<StudentChecking> findBySecondaryOwnerUserName(String secondaryOwnerUserName);


    Optional<StudentChecking> findByPrimaryOwnerId(Long primaryOwnerId);
}
