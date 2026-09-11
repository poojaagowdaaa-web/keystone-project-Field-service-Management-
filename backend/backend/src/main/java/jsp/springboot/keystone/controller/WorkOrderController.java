package jsp.springboot.keystone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jsp.springboot.keystone.WorkOrder;
import jsp.springboot.keystone.WorkOrderDTO;
import jsp.springboot.keystone.WorkOrderStatus;
import jsp.springboot.keystone.service.WorkOrderService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/workorders")
public class WorkOrderController {

    @Autowired
    private WorkOrderService workOrderService;

    // GET all work orders
    @GetMapping
    public List<WorkOrder> getAllWorkOrders() {
        return workOrderService.getAllWorkOrders();
    }

    // GET one work order by id
    @GetMapping("/{id}")
    public ResponseEntity<WorkOrder> getWorkOrderById(@PathVariable Long id) {
        WorkOrder workOrder = workOrderService.getWorkOrderById(id);
        if (workOrder == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(workOrder);
    }

    // CREATE a new work order (validated via DTO)
    @PostMapping
    public WorkOrder createWorkOrder(@Valid @RequestBody WorkOrderDTO dto) {
        WorkOrder workOrder = new WorkOrder();
        workOrder.setTitle(dto.getTitle());
        workOrder.setDescription(dto.getDescription());
        workOrder.setStatus(WorkOrderStatus.valueOf(dto.getStatus()));
        return workOrderService.createWorkOrder(workOrder);
    }

    // UPDATE an existing work order (validated via DTO)
    @PutMapping("/{id}")
    public ResponseEntity<WorkOrder> updateWorkOrder(@PathVariable Long id, @Valid @RequestBody WorkOrderDTO dto) {
        WorkOrder workOrder = new WorkOrder();
        workOrder.setTitle(dto.getTitle());
        workOrder.setDescription(dto.getDescription());
        workOrder.setStatus(WorkOrderStatus.valueOf(dto.getStatus()));

        WorkOrder updated = workOrderService.updateWorkOrder(id, workOrder);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // DELETE a work order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkOrder(@PathVariable Long id) {
        boolean deleted = workOrderService.deleteWorkOrder(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}