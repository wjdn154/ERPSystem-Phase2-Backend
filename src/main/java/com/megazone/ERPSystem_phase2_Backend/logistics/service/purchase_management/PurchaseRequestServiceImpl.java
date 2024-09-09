package com.megazone.ERPSystem_phase2_Backend.logistics.service.purchase_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseRequestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.PurchaseRequest;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management.PurchaseRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseRequestServiceImpl implements PurchaseRequestService {

    private final PurchaseRequestRepository purchaseRequestRepository;

    public List<PurchaseRequestDTO> getAllPurchaseRequests() {
        return purchaseRequestRepository.findAll().stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    public PurchaseRequestDTO getPurchaseRequestById(Long id) {
        return purchaseRequestRepository.findById(id)
                .map(this::convertEntityToDTO)
                .orElseThrow(() -> new RuntimeException("PurchaseRequest not found with id " + id));
    }

    public PurchaseRequestDTO createPurchaseRequest(PurchaseRequestDTO purchaseRequestDTO) {
        PurchaseRequest purchaseRequest = convertDTOToEntity(purchaseRequestDTO);
        return convertEntityToDTO(purchaseRequestRepository.save(purchaseRequest));
    }

    public PurchaseRequestDTO updatePurchaseRequest(Long id, PurchaseRequestDTO purchaseRequestDTO) {
        return purchaseRequestRepository.findById(id).map(purchaseRequest -> {
            PurchaseRequest updatedPurchaseRequest = PurchaseRequest.builder()
                    .id(purchaseRequest.getId())
                    .company(purchaseRequest.getCompany()) // 회사 정보는 업데이트 하지 않기 때문에 기존 값을 재사용합니다.
                    .clientId(purchaseRequestDTO.getClientId())
                    .employeeId(purchaseRequestDTO.getEmployeeId())
                    .productId(purchaseRequestDTO.getProductId())
                    .quantity(purchaseRequestDTO.getQuantity())
                    .supplyPrice(purchaseRequestDTO.getSupplyPrice())
                    .vatType(purchaseRequestDTO.getVatType())
                    .vat(purchaseRequestDTO.getVat())
                    .date(purchaseRequestDTO.getDate())
                    .deliveryDate(purchaseRequestDTO.getDeliveryDate())
                    .remarks(purchaseRequestDTO.getRemarks())
                    .build();
            return convertEntityToDTO(purchaseRequestRepository.save(updatedPurchaseRequest));
        }).orElseThrow(() -> new RuntimeException("PurchaseRequest not found with id " + id));
    }

    public void deletePurchaseRequest(Long id) {
        purchaseRequestRepository.deleteById(id);
    }

    private PurchaseRequestDTO convertEntityToDTO(PurchaseRequest purchaseRequest) {

        return PurchaseRequestDTO.builder()
                .id(purchaseRequest.getId())
                .clientId(purchaseRequest.getClientId())
                .employeeId(purchaseRequest.getEmployeeId())
                .warehouseId(purchaseRequest.getWarehouse().getId())
                .currencyId(purchaseRequest.getCurrency().getId())
                .productId(purchaseRequest.getProductId())
                .quantity(purchaseRequest.getQuantity())
                .supplyPrice(purchaseRequest.getSupplyPrice())
                .vatType(purchaseRequest.getVatType())
                .vat(purchaseRequest.getVat())
                .date(purchaseRequest.getDate())
                .deliveryDate(purchaseRequest.getDeliveryDate())
                .remarks(purchaseRequest.getRemarks())
                .build();
    }

    private PurchaseRequest convertDTOToEntity(PurchaseRequestDTO purchaseRequestDTO) {
        return PurchaseRequest.builder()
                .id(purchaseRequestDTO.getId())
                .clientId(purchaseRequestDTO.getClientId())
                .employeeId(purchaseRequestDTO.getEmployeeId())
                .productId(purchaseRequestDTO.getProductId())
                .quantity(purchaseRequestDTO.getQuantity())
                .supplyPrice(purchaseRequestDTO.getSupplyPrice())
                .vatType(purchaseRequestDTO.getVatType())
                .vat(purchaseRequestDTO.getVat())
                .date(purchaseRequestDTO.getDate())
                .deliveryDate(purchaseRequestDTO.getDeliveryDate())
                .remarks(purchaseRequestDTO.getRemarks())
                .build();
    }
}