package com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessRoutingDTO {
    private Long id;
    private String code;
    private String name;
    private String description;
    private boolean isStandard;
    private boolean isActive;


    private List<RoutingStepDTO> routingSteps; // 연관 RoutingStep 목록
    private List<ProductDto> products; // 연관 Product 목록
//    private Long productId;

    public void setDeletedAt(LocalDateTime now) {
    }
}
