package com.megazone.ERPSystem_phase2_Backend.logistics.service.purchase_management.purchase;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseResponseDto;

import java.util.List;

public interface PurchaseService {
    List<PurchaseResponseDto> findAllPurchases();
}
