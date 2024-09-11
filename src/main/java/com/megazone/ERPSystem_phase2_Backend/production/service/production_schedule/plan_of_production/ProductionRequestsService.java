package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.plan_of_production;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.ProductionRequestsDTO;

import java.util.List;

public interface ProductionRequestsService {
    ProductionRequestsDTO saveManualProductionRequest(ProductionRequestsDTO productionRequestsDTO);

    ProductionRequestsDTO getProductionRequestById(Long id);

    List<ProductionRequestsDTO> getAllProductionRequests();

    ProductionRequestsDTO updateProductionRequest(Long id, ProductionRequestsDTO productionRequestsDTO);

    void deleteProductionRequest(Long id);

}
