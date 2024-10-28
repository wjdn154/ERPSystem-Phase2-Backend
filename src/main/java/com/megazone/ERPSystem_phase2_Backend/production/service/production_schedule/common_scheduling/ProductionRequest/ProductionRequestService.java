package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.common_scheduling.ProductionRequest;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.ProductionRequestListDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.ProductionRequestDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.enums.ProgressType;

import java.util.List;

public interface ProductionRequestService {
    ProductionRequestDetailDTO saveManualProductionRequest(ProductionRequestDetailDTO dto);

    ProductionRequestDetailDTO getProductionRequestById(Long id);

    List<ProductionRequestListDTO> getAllProductionRequests();

    ProductionRequestDetailDTO updateProductionRequest(Long id, ProductionRequestDetailDTO dto);

    void deleteProductionRequest(Long id);

    ProductionRequestDetailDTO confirmProductionRequest(Long id, Long confirmedQuantity);

    void updateProgress(Long requestId, ProgressType progressType);
}
