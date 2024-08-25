package com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductDetailDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductionRoutingDTO {
    private Long id;
    private String code;
    private String name;
    private String description;
    private boolean isStandard;
    private boolean isActive;
    private List<RoutingStepDTO> routingStepDTOList; // 연관 RoutingStep 목록
    private List<ProductDetailDto> products; // 연관 Product 목록

    public void setDeletedAt(LocalDateTime now) {

    }
}
