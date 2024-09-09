package com.megazone.ERPSystem_phase2_Backend.logistics.service.purchase_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseRequestDTO;

import java.util.List;

public interface PurchaseRequestService {
    List<PurchaseRequestDTO> getAllPurchaseRequests();

    PurchaseRequestDTO getPurchaseRequestById(Long id);

    PurchaseRequestDTO createPurchaseRequest(PurchaseRequestDTO purchaseRequestDTO);

    PurchaseRequestDTO updatePurchaseRequest(Long id, PurchaseRequestDTO purchaseRequestDTO);

    void deletePurchaseRequest(Long id);
}
