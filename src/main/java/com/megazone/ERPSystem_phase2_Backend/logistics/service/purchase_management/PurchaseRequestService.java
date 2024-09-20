package com.megazone.ERPSystem_phase2_Backend.logistics.service.purchase_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseRequestResponseDto;

import java.util.List;

public interface PurchaseRequestService {

    List<PurchaseRequestResponseDto> findAllPurchaseRequests();
}
