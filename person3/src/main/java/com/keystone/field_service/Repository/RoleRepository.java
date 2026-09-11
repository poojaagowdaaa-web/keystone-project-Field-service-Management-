package com.keystone.field_service.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.keystone.field_service.Entity.Role;
import java.util.Optional;
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}