package com.keystone.field_service.Repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keystone.field_service.Entity.Role;
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}