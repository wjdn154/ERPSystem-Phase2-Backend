package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.common_scheduling.ProductionRequest;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.ProductionRequestDTO;

import java.util.List;

public interface ProductionRequestService {
    ProductionRequestDTO saveManualProductionRequest(ProductionRequestDTO productionRequestDTO);

    ProductionRequestDTO getProductionRequestById(Long id);

    List<ProductionRequestDTO> getAllProductionRequests();

    ProductionRequestDTO updateProductionRequest(Long id, ProductionRequestDTO productionRequestDTO);

    void deleteProductionRequest(Long id);

}
