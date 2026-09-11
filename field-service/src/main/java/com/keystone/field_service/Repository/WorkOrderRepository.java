package com.keystone.field_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keystone.field_service.Entity.WorkOrder;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {

}
