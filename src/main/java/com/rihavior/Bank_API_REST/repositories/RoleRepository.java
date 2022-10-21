package com.rihavior.Bank_API_REST.repositories;

import com.rihavior.Bank_API_REST.others.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
