package com.megazone.ERPSystem_phase2_Backend.logistics.service.sales_management.sale;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.SearchDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.sale.SaleCreateDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.sale.SaleResponseDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.sale.SaleResponseDto;

import java.util.List;
import java.util.Optional;

public interface SaleService {
    List<SaleResponseDto> findAllSales(SearchDTO dto);

    Optional<SaleResponseDetailDto> findSaleDetailById(Long id);

    SaleResponseDetailDto createSale(SaleCreateDto createDto);

    SaleResponseDetailDto updateSale(Long id, SaleCreateDto updateDto);

    String deleteSale(Long id);
}
