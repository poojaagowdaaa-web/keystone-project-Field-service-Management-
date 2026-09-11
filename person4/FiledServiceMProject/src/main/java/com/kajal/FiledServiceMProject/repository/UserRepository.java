package com.kajal.FiledServiceMProject.repository;

import com.kajal.FiledServiceMProject.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}