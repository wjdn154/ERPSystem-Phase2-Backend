package com.megazone.ERPSystem_phase2_Backend.logistics.service.purchase_management.purchase;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseCreateDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseResponseDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseResponseDto;

import java.util.List;
import java.util.Optional;

public interface PurchaseService {
    List<PurchaseResponseDto> findAllPurchases();

    Optional<PurchaseResponseDetailDto> findPurchaseDetailById(Long id);

    PurchaseResponseDetailDto createPurchase(PurchaseCreateDto createDto);

    PurchaseResponseDetailDto updatePurchase(Long id, PurchaseCreateDto updateDto);

    String deletePurchase(Long id);
}
