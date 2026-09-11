package com.keystone.field_service.Controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.keystone.field_service.Entity.WorkOrder;
import com.keystone.field_service.Service.WorkOrderService;

@RestController
@RequestMapping("/api/work-orders")
public class WorkOrderController {

    private final WorkOrderService workOrderService;

    public WorkOrderController(WorkOrderService workOrderService) {
        this.workOrderService = workOrderService;
    }

    @PostMapping
    public ResponseEntity<WorkOrder> createWorkOrder(
            @RequestBody WorkOrder workOrder) {

        return ResponseEntity.ok(
                workOrderService.createWorkOrder(workOrder)
        );
    }

    @GetMapping
    public ResponseEntity<List<WorkOrder>> getAllWorkOrders() {

        return ResponseEntity.ok(
                workOrderService.getAllWorkOrders()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkOrder> getWorkOrderById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                workOrderService.getWorkOrderById(id)
        );
        
    }
    @PutMapping("/{workOrderId}/start")
    public ResponseEntity<WorkOrder> startWorkOrder(
            @PathVariable Long workOrderId) {

        return ResponseEntity.ok(
                workOrderService.startWorkOrder(workOrderId)
        );
    }
}
