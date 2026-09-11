package jsp.springboot.keystone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jsp.springboot.keystone.WorkOrder;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {
	}