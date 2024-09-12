package com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.process_routing.ProcessRouting;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductDetailDto;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.ProcessRouting;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.dto.ProcessDetailsDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.dto.ProcessRoutingDTO;

import java.util.List;

public interface ProcessRoutingService {

//    List<ProductionRoutingDTO> getAllProductionRoutings();
//    ProductionRoutingDTO getProductionRoutingById(Long id);
//    List<ProductionRoutingDTO> searchProductionRoutings(String code, String name, Boolean isActive);

    // Routing 생성 시 RoutingStep 순서 지정해서 등록
//    ProductionRouting createProductionRoutingWithSteps(ProductionRoutingDTO routingDTO, List<RoutingStepDTO> stepDTOs);
    ProcessRoutingDTO createProcessRoutingWithSteps(ProcessRoutingDTO routingDTO);

    // ProductionRouting 업데이트 로직
    ProcessRoutingDTO updateProcessRouting(Long id, ProcessRoutingDTO processRoutingDTO);

    // ProductionRouting 삭제 로직
    ProcessRoutingDTO deleteProcessRouting(Long id);

    ProcessRoutingDTO convertToDTO(ProcessRouting processRouting);

    // Search
    List<ProcessDetailsDTO> searchProcessDetails(String keyword);
    List<ProductDetailDto> searchProducts(String keyword);

    // Get
    ProductDetailDto getProductById(Long id);
    ProcessDetailsDTO getProcessDetailsById(Long id);

}