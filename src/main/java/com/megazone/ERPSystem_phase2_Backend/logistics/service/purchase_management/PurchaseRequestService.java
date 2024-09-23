package com.megazone.ERPSystem_phase2_Backend.logistics.service.purchase_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseRequestCreateDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseRequestResponseDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseRequestResponseDto;

import java.util.List;
import java.util.Optional;

public interface PurchaseRequestService {

    List<PurchaseRequestResponseDto> findAllPurchaseRequests();

    Optional<PurchaseRequestResponseDetailDto> findPurchaseRequestDetailById(Long id);

    PurchaseRequestResponseDetailDto createPurchaseRequest(PurchaseRequestCreateDto creationDto);

    PurchaseRequestResponseDetailDto updatePurchaseRequest(Long id, PurchaseRequestCreateDto updateDto);

    String deletePurchaseRequest(Long id);
}
