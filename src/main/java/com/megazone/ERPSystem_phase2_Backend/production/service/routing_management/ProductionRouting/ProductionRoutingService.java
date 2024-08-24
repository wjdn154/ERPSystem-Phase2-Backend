package com.megazone.ERPSystem_phase2_Backend.production.service.routing_management.ProductionRouting;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductDetailDto;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProductionRouting;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto.ProcessDetailsDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto.ProductionRoutingDTO;

import java.util.List;

public interface ProductionRoutingService {

//    List<ProductionRoutingDTO> getAllProductionRoutings();
//    ProductionRoutingDTO getProductionRoutingById(Long id);
//    List<ProductionRoutingDTO> searchProductionRoutings(String code, String name, Boolean isActive);

    // Routing 생성 시 RoutingStep 순서 지정해서 등록
//    ProductionRouting createProductionRoutingWithSteps(ProductionRoutingDTO routingDTO, List<RoutingStepDTO> stepDTOs);
    ProductionRoutingDTO createProductionRoutingWithSteps(ProductionRoutingDTO routingDTO);

    // ProductionRouting 업데이트 로직
    ProductionRoutingDTO updateProductionRouting(Long id, ProductionRoutingDTO productionRoutingDTO);

    // ProductionRouting 삭제 로직
    ProductionRoutingDTO deleteProductionRouting(Long id);

    ProductionRoutingDTO convertToDTO(ProductionRouting productionRouting);

    // Search
    List<ProcessDetailsDTO> searchProcessDetails(String keyword);
    List<ProductDetailDto> searchProducts(String keyword);

    // Get
    ProductDetailDto getProductById(Long id);
    ProcessDetailsDTO getProcessDetailsById(Long id);

}
