package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.common_scheduling.ProductionOrder;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.ProductionOrderDTO;

import java.util.List;

public interface ProductionOrderService {
    ProductionOrderDTO getProductionOrderById(Long productionOrderId);

    List<ProductionOrderDTO> getAllProductionOrders();

    ProductionOrderDTO createProductionOrder(ProductionOrderDTO productionOrderDTO);
    ProductionOrderDTO updateProductionOrder(Long productionOrderId, ProductionOrderDTO productionOrderDTO);

    void deleteProductionOrder(Long productionOrderId);

    void assignWorkersToWorkcenter(ProductionOrderDTO productionOrderDTO);

    }
