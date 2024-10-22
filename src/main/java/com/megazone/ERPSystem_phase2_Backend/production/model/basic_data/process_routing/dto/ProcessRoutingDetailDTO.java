package com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.dto;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductDto;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.ProcessDetails;
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
public class ProcessRoutingDetailDTO {
    private Long id;
    private String code;
    private String name;
    private String description;
    private boolean isStandard;
    private boolean isActive;

    private List<ProcessDetails> processDetails;
    private List<ProductDto> products;
}
