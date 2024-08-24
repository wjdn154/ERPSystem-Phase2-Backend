package com.megazone.ERPSystem_phase2_Backend.production.repository.routing_management.ProductionRouting;

import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProductionRouting;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto.ProductionRoutingDTO;

import java.util.List;

public interface ProductionRoutingRepositoryCustom {

    List<ProductionRouting> findRoutingsByProcessDetails(Long processId);
    List<ProductionRouting> findRoutingsByProduct(Long prooductId);


//    List<ProductionRoutingDTO> findAllAsDTO();
//    ProductionRoutingDTO findByIdAsDTO(Long id);
//    List<ProductionRoutingDTO> searchProductionRoutings(String code, String name, Boolean isActive);

}
