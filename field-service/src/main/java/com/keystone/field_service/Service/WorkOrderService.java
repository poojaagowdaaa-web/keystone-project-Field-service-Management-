package com.keystone.field_service.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.keystone.field_service.Entity.User;
import com.keystone.field_service.Entity.WorkOrder;
import com.keystone.field_service.Repository.UserRepository;
import com.keystone.field_service.Repository.WorkOrderRepository;

@Service
public class WorkOrderService {

    private final WorkOrderRepository workOrderRepository;
    private final UserRepository userRepository;

    public WorkOrderService(
            WorkOrderRepository workOrderRepository,
            UserRepository userRepository) {

        this.workOrderRepository = workOrderRepository;
        this.userRepository = userRepository;
    }

    public WorkOrder createWorkOrder(WorkOrder workOrder) {

        return workOrderRepository.save(workOrder);
    }

    public List<WorkOrder> getAllWorkOrders() {

        return workOrderRepository.findAll();
    }

    public WorkOrder getWorkOrderById(Long id) {

        return workOrderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Work order not found"));
    }

    public WorkOrder assignTechnician(Long workOrderId, Long technicianId) {

        WorkOrder workOrder = workOrderRepository.findById(workOrderId)
                .orElseThrow(() ->
                        new RuntimeException("Work order not found"));

        User technician = userRepository.findById(technicianId)
                .orElseThrow(() ->
                        new RuntimeException("Technician not found"));

        workOrder.setTechnician(technician);
        workOrder.setStatus("ASSIGNED");

        return workOrderRepository.save(workOrder);
    }
    public WorkOrder startWorkOrder(Long workOrderId) {

        WorkOrder workOrder = workOrderRepository.findById(workOrderId)
                .orElseThrow(() ->
                        new RuntimeException("Work order not found"));

        if (!"ASSIGNED".equals(workOrder.getStatus())) {
            throw new RuntimeException(
                    "Work order must be ASSIGNED before starting");
        }

        workOrder.setStatus("IN_PROGRESS");

        return workOrderRepository.save(workOrder);
    }
}