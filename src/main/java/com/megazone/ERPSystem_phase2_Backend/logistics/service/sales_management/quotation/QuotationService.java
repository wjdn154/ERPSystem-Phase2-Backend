package com.megazone.ERPSystem_phase2_Backend.logistics.service.sales_management.quotation;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.SearchDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.quotation.QuotationCreateDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.quotation.QuotationResponseDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.quotation.QuotationResponseDto;

import java.util.List;
import java.util.Optional;

public interface QuotationService {
    List<QuotationResponseDto> findAllQuotations(SearchDTO dto);

    Optional<QuotationResponseDetailDto> findQuotationDetailById(Long id);

    QuotationResponseDetailDto createQuotation(QuotationCreateDto createDto);

    QuotationResponseDetailDto updateQuotation(Long id, QuotationCreateDto updateDto);

    String deleteQuotation(Long id);
}
