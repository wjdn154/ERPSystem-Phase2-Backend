package com.megazone.ERPSystem_phase2_Backend.production.service.routing_management.ProductionRouting;

import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProductionRouting;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto.ProductionRoutingDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto.RoutingStepDTO;

import java.util.List;
import java.util.Optional;

public interface ProductionRoutingService {

    // Routing 생성 시 RoutingStep 순서 지정해서 등록
//    ProductionRouting createProductionRoutingWithSteps(ProductionRoutingDTO routingDTO, List<RoutingStepDTO> stepDTOs);
    ProductionRouting createProductionRoutingWithSteps(ProductionRoutingDTO routingDTO);

    // ProductionRouting 업데이트 로직
    Optional<ProductionRouting> updateProductionRouting(Long id, ProductionRoutingDTO productionRoutingDTO);

    // ProductionRouting 삭제 로직
    Optional<ProductionRouting> deleteProductionRouting(Long id);

    ProductionRoutingDTO convertToDTO(ProductionRouting productionRouting);
}
