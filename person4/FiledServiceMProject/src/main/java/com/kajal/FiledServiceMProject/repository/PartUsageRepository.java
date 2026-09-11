package com.kajal.FiledServiceMProject.repository;

import com.kajal.FiledServiceMProject.Entity.PartUsage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PartUsageRepository extends JpaRepository<PartUsage, Long> {
    List<PartUsage> findByWorkOrderId(Long workOrderId);
}