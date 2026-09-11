package com.kajal.FiledServiceMProject.repository;

import com.kajal.FiledServiceMProject.Entity.WorkOrderStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface WorkOrderStatusHistoryRepository extends JpaRepository<WorkOrderStatusHistory, Long> {
    List<WorkOrderStatusHistory> findByWorkOrderIdOrderByChangedAtAsc(Long workOrderId);
}