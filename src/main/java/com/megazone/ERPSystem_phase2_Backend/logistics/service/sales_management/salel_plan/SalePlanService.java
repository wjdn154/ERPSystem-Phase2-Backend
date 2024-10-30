package com.megazone.ERPSystem_phase2_Backend.logistics.service.sales_management.salel_plan;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.SearchDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.sale_plan.SalePlanCreateDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.sale_plan.SalePlanResponseDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.sale_plan.SalePlanResponseDto;

import java.util.List;
import java.util.Optional;

public interface SalePlanService {
    List<SalePlanResponseDto> findAllSalePlans(SearchDTO dto);

    Optional<SalePlanResponseDetailDto> findSalePlanDetailById(Long id);

    SalePlanResponseDetailDto createSalePlan(SalePlanCreateDto createDto);

    SalePlanResponseDetailDto updateSalePlan(Long id, SalePlanCreateDto updateDto);

    String deleteSalePlan(Long id);
}
