package com.megazone.ERPSystem_phase2_Backend.logistics.service.purchase_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.PurchaseRequest;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.PurchaseRequestDetail;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseRequestResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management.PurchaseRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseRequestServiceImpl implements PurchaseRequestService {

    private final PurchaseRequestRepository purchaseRequestRepository;


    @Override
    public Object findAllPurchaseRequests() {
        // 모든 발주 요청을 가져옴
        List<PurchaseRequest> purchaseRequests = purchaseRequestRepository.findAll();

        if (purchaseRequests.isEmpty()) {
            return ResponseEntity.badRequest().body("발주요청이 없습니다.");
        }

        // 각 발주 요청을 Response DTO로 변환
        return purchaseRequests.stream()
                .map(this::toDto)  // 각 발주 요청을 Response DTO로 변환
                .collect(Collectors.toList());
    }

    // Entity -> DTO 변환 메소드
    private PurchaseRequestResponseDto toDto(PurchaseRequest purchaseRequest) {
        return PurchaseRequestResponseDto.builder()
                .id(purchaseRequest.getId())
                .clientName(getClientNameFromFirstProduct(purchaseRequest))  // 첫 번째 품목의 거래처 이름
                .productName(getProductNameWithCount(purchaseRequest))  // 첫 번째 품목 이름과 "외 몇건" 추가
                .date(purchaseRequest.getDate())
                .deliveryDate(purchaseRequest.getDeliveryDate())
                .totalQuantity(getTotalQuantity(purchaseRequest))  // 총 수량
                .totalPrice(getTotalPrice(purchaseRequest))  // 총 가격
                .status(purchaseRequest.getStatus().toString())  // 진행 상태
                .build();
    }


    // 첫 번째 품목의 거래처 이름 가져오기
    private String getClientNameFromFirstProduct(PurchaseRequest purchaseRequest) {
        if (!purchaseRequest.getPurchaseRequestDetails().isEmpty()) {
            return purchaseRequest.getPurchaseRequestDetails().get(0).getProduct().getClient().getPrintClientName();
        }
        return null;
    }

    // 첫 번째 품목 이름과 외 몇건 처리
    private String getProductNameWithCount(PurchaseRequest purchaseRequest) {
        List<PurchaseRequestDetail> details = purchaseRequest.getPurchaseRequestDetails();

        if (details.isEmpty()) {
            return "품목 없음";  // 품목이 없을 경우 처리
        }

        String firstProductName = details.get(0).getProduct().getName();

        // 품목이 2개 이상일 때 "외 N건"을 붙임
        if (details.size() > 1) {
            return firstProductName + " 외 " + (details.size() - 1) + "건";
        } else {
            return firstProductName;  // 품목이 하나일 경우 그냥 첫 번째 품목 이름만 반환
        }
    }

    // 총 수량 계산
    private int getTotalQuantity(PurchaseRequest purchaseRequest) {
        return purchaseRequest.getPurchaseRequestDetails().stream()
                .mapToInt(PurchaseRequestDetail::getQuantity)
                .sum();
    }

    // 총 금액 계산
    private double getTotalPrice(PurchaseRequest purchaseRequest) {
        return purchaseRequest.getPurchaseRequestDetails().stream()
                .mapToDouble(PurchaseRequestDetail::getSupplyPrice)
                .sum();
    }
}