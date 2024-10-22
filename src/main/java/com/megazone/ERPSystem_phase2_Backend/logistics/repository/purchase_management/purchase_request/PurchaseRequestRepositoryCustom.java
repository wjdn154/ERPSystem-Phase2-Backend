package com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management.purchase_request;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseRequestResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.SearchDTO;

import java.util.List;

public interface PurchaseRequestRepositoryCustom {

    List<PurchaseRequestResponseDto> searchPurchaseRequests(SearchDTO dto);
}
