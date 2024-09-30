package com.megazone.ERPSystem_phase2_Backend.logistics.service.purchase_management.purchase_order;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.PurchaseOrder;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.PurchaseRequestDetail;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseOrderResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management.purchase_order.PurchaseOrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PurchaseOrderServiceImpl implements PurchaseOrderService{

    private final PurchaseOrderRepository purchaseOrderRepository;

    @Override
    public List<PurchaseOrderResponseDto> findAllPurchaseOrders() {

        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();

        if (purchaseOrders.isEmpty()) {
            return new ArrayList<>();
        }

        return purchaseOrders.stream()
                .map(this::toListDto)
                .toList();
    }

    private PurchaseOrderResponseDto toListDto(PurchaseOrder purchaseOrder) {
        return PurchaseOrderResponseDto.builder()
                .id(purchaseOrder.getId())
                .clientName(getClientNameFromFirstProduct(purchaseOrder))  // 첫 번째 품목의 거래처 이름
                .productName(getProductNameWithCount(purchaseOrder))  // 첫 번째 품목 이름과 "외 몇건" 추가
                .date(purchaseOrder.getDate())
                .deliveryDate(purchaseOrder.getDeliveryDate())
                .totalQuantity(getTotalQuantity(purchaseOrder))  // 총 수량
                .totalPrice(getTotalPrice(purchaseOrder))  // 총 가격
                .status(purchaseOrder.getStatus().toString())  // 진행 상태
                .build();
    }

    private Double getTotalPrice(PurchaseOrder purchaseOrder) {
        return purchaseOrder.getPurchaseRequest().getPurchaseRequestDetails().stream()
                .mapToDouble(PurchaseRequestDetail::getSupplyPrice)
                .sum();
    }

    private Integer getTotalQuantity(PurchaseOrder purchaseOrder) {
        return purchaseOrder.getPurchaseRequest().getPurchaseRequestDetails().stream()
                .mapToInt(PurchaseRequestDetail::getQuantity)
                .sum();
    }

    private String getProductNameWithCount(PurchaseOrder purchaseOrder) {
        List<PurchaseRequestDetail> details = purchaseOrder.getPurchaseRequest().getPurchaseRequestDetails();

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

    private String getClientNameFromFirstProduct(PurchaseOrder purchaseOrder) {
        if (purchaseOrder.getPurchaseRequest().getId() != null) {
            return purchaseOrder.getPurchaseRequest().getPurchaseRequestDetails().get(0).getProduct().getClient().getPrintClientName();
        }
        return purchaseOrder.getClient().getPrintClientName();
    }
}
