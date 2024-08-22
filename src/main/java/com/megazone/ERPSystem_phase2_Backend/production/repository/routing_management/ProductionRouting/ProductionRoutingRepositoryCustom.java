package com.megazone.ERPSystem_phase2_Backend.production.repository.routing_management.ProductionRouting;

import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProductionRouting;

import java.util.List;

public interface ProductionRoutingRepositoryCustom {

    List<ProductionRouting> findRoutingsByProcessDetails(Long processId);
//    List<ProductionRouting> findRoutingsByProduct(Long prooductId); // TODO Routing에 Product 추가한 후 수정

}
