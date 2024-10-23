package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.common_scheduling.ProductionOrder;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.ProductionOrder;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.ProductionOrderDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.Mps;

import java.util.List;

public interface ProductionOrderService {
    ProductionOrderDTO getProductionOrderById(Long productionOrderId);

    List<ProductionOrderDTO> getAllProductionOrders();

    ProductionOrderDTO saveProductionOrder(ProductionOrderDTO productionOrderDTO);
    ProductionOrderDTO updateProductionOrder(Long productionOrderId, ProductionOrderDTO productionOrderDTO);

    void deleteProductionOrder(Long productionOrderId);

    void assignWorkersToWorkcenter(ProductionOrderDTO productionOrderDTO, ProductionOrder productionOrder);
    void updateOrderClosure(Long productionOrderId);

    void createOrdersFromMps(Mps savedMps);

}
