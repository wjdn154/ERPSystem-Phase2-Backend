package com.megazone.ERPSystem_phase2_Backend.logistics.service.purchase_management.purchase;

import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.client.ClientRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.Purchase;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.PurchaseDetail;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.PurchaseOrder;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.PurchaseOrderDetail;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management.CurrencyRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management.purchase.PurchaseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final WarehouseRepository warehouseRepository;
    private final CurrencyRepository currencyRepository;
    private final ProductRepository productRepository;


    /**
     * 구매서 목록 조회
     * @return
     */
    @Override
    public List<PurchaseResponseDto> findAllPurchases() {

        List<Purchase> purchases = purchaseRepository.findAll();

        if (purchases.isEmpty()) {
            return new ArrayList<>();
        }

        return purchases.stream()
                .map(this::toListDto)
                .toList();
    }

    /** 구매서 목록 조회 관련 메서드 **/
    // Entity -> 구매서 목록 조회용 DTO 변환 메소드
    private PurchaseResponseDto toListDto(Purchase purchase) {

        return PurchaseResponseDto.builder()
                .id(purchase.getId())
                .clientName(purchase.getClient().getPrintClientName())
                .productName(getProductNameWithCount(purchase))
                .warehouseName(purchase.getReceivingWarehouse().getName())
                .vatName(purchase.getVatId().toString())
                .totalPrice(getTotalPrice(purchase))
                .status(purchase.getStatus().toString())
                .accountingReflection(purchase.getAccountingReflection())
                .build();
    }

    private BigDecimal getTotalPrice(Purchase purchase) {
        return purchase.getPurchaseDetails().stream()
                .map(PurchaseDetail::getSupplyPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private String getProductNameWithCount(Purchase purchase) {
        List<PurchaseDetail> details = purchase.getPurchaseDetails();

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


}

