package com.kajal.FiledServiceMProject.repository;

import com.kajal.FiledServiceMProject.Entity.TimeLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TimeLogRepository extends JpaRepository<TimeLog, Long> {
    List<TimeLog> findByWorkOrderId(Long workOrderId);
}