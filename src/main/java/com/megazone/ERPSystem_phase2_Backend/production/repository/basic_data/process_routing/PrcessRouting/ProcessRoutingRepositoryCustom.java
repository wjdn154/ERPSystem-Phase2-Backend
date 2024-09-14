package com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.process_routing.PrcessRouting;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.ProcessRouting;

import java.util.List;

public interface ProcessRoutingRepositoryCustom {

    List<ProcessRouting> findRoutingsByProcessDetails(Long processId);
    List<ProcessRouting> findRoutingsByProduct(Long prooductId);


//    List<ProductionRoutingDTO> findAllAsDTO();
//    ProductionRoutingDTO findByIdAsDTO(Long id);
//    List<ProductionRoutingDTO> searchProductionRoutings(String code, String name, Boolean isActive);

}
