package com.megazone.ERPSystem_phase2_Backend.logistics.repository.sales_management.quotation;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.SearchDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.Quotation;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.SalePlan;

import java.util.List;

public interface QuotationRepositoryCustom {
    List<Quotation> findBySearch(SearchDTO dto);
}
