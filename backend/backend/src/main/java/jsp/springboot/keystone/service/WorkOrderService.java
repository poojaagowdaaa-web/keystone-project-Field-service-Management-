package jsp.springboot.keystone.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jsp.springboot.keystone.WorkOrder;
import jsp.springboot.keystone.repository.WorkOrderRepository;

@Service
public class WorkOrderService {

    @Autowired
    private WorkOrderRepository workOrderRepository;

    public List<WorkOrder> getAllWorkOrders() {
        return workOrderRepository.findAll();
    }

    public WorkOrder getWorkOrderById(Long id) {
        return workOrderRepository.findById(id).orElse(null);
    }

    public WorkOrder createWorkOrder(WorkOrder workOrder) {
        return workOrderRepository.save(workOrder);
    }

    public WorkOrder updateWorkOrder(Long id, WorkOrder updatedWorkOrder) {
        WorkOrder existing = workOrderRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        existing.setTitle(updatedWorkOrder.getTitle());
        existing.setDescription(updatedWorkOrder.getDescription());
        existing.setStatus(updatedWorkOrder.getStatus());
        existing.setSite(updatedWorkOrder.getSite());
        return workOrderRepository.save(existing);
    }

    public boolean deleteWorkOrder(Long id) {
        if (!workOrderRepository.existsById(id)) {
            return false;
        }
        workOrderRepository.deleteById(id);
        return true;
    }
}
