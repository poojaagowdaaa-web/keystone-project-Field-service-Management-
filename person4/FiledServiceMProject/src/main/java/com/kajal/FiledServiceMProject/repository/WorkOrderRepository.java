package com.kajal.FiledServiceMProject.repository;

import com.kajal.FiledServiceMProject.Entity.WorkOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {
    Page<WorkOrder> findByStatus(String status, Pageable pageable);
    Page<WorkOrder> findByAssignedToId(Long userId, Pageable pageable);
    Page<WorkOrder> findByCustomerId(Long customerId, Pageable pageable);
}