package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.work_order;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.WorkOrderDTO;

import java.util.List;

public interface WorkOrderService {
    WorkOrderDTO getWorkOrderById(Long workOrderId);

    List<WorkOrderDTO> getAllWorkOrders();

    WorkOrderDTO createWorkOrder(WorkOrderDTO workOrderDTO);
    WorkOrderDTO updateWorkOrder(Long workOrderId, WorkOrderDTO workOrderDTO);

    void deleteWorkOrder(Long workOrderId);

    void assignWorkersToWorkcenter(WorkOrderDTO workOrderDTO);


}
