package com.kajal.FiledServiceMProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


import com.kajal.FiledServiceMProject.Entity.Organization;


public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Optional<Organization> findByInviteCode(String inviteCode);
}