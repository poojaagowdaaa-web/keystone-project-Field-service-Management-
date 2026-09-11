package com.kajal.FiledServiceMProject.repository;

import com.kajal.FiledServiceMProject.Entity.Site;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SiteRepository extends JpaRepository<Site, Long> {
    Page<Site> findByCustomerId(Long customerId, Pageable pageable);
}