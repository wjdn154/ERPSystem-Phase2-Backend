package com.megazone.ERPSystem_phase2_Backend.logistics.service.sales_management.salel_plan;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.SearchDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.sale_plan.SalePlanResponseDto;

import java.util.List;

public interface SalePlanService {
    List<SalePlanResponseDto> findAllSalePlans(SearchDTO dto);
}
