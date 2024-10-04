package com.megazone.ERPSystem_phase2_Backend.financial.controller.voucher_entry.sales_and_purchase_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatAmountWithQuantityPriceDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatAmountWithSupplyAmountDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatTypeShowAllDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatTypeShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.enums.TransactionType;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.vatType.VatTypeRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.sales_and_purchase_voucher_entry.VatTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class VatTypeApiController {

    private final VatTypeRepository vatTypeRepository;
    private final VatTypeService vatTypeService;


    @PostMapping("/api/financial/vatType/show")
    public ResponseEntity<VatTypeShowAllDTO> vatTypeShow() {
        VatTypeShowAllDTO vatTypeShowAllDTO = new VatTypeShowAllDTO();
        List<VatTypeShowDTO> salesVatType = vatTypeRepository.findAllByTransactionType(TransactionType.SALES)
                .stream().map(VatTypeShowDTO::create).toList();
        List<VatTypeShowDTO> purchaseVatType = vatTypeRepository.findAllByTransactionType(TransactionType.PURCHASE)
                .stream().map(VatTypeShowDTO::create).toList();
        vatTypeShowAllDTO.setSalesVatTypeShowDTO(salesVatType);
        vatTypeShowAllDTO.setPurchaseVatTypeShowDTO(purchaseVatType);

        if(vatTypeShowAllDTO == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(vatTypeShowAllDTO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(vatTypeShowAllDTO);
    }
    // 수량, 단가, 부가세유형 코드 = 부가세 금액
    @PostMapping("/api/financial/vatType/vatAmount/quantityPrice")
    public ResponseEntity<Object> vatAmountWithQuantityPrice(@RequestBody VatAmountWithQuantityPriceDTO dto) {

        try {
            BigDecimal vatAmount = vatTypeService.vatAmountCalculate(dto);
            return ResponseEntity.status(HttpStatus.OK).body(vatAmount);
        }
        catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // 공급가액, 부가세유형 코드 = 부가세 금액
    @PostMapping("/api/financial/vatType/vatAmount/supplyAmount")
    public ResponseEntity<Object> vatAmountSupplyAmount(@RequestBody VatAmountWithSupplyAmountDTO dto) {
        try {
            BigDecimal vatAmount = vatTypeService.vatAmountCalculate(dto);
            return ResponseEntity.status(HttpStatus.OK).body(vatAmount);
        }
        catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
    @PostMapping("/api/financial/vatType/vatType/id")
    private ResponseEntity<Object> vatTypeGetId(@RequestParam String vatTypeCode) {
        try {
            Long vatTypeId = vatTypeService.vatTypeGetId(vatTypeCode);
            return ResponseEntity.status(HttpStatus.OK).body(vatTypeId);
        }
        catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @PostMapping("/api/financial/vatType/vatType/get")
    private ResponseEntity<Object> vatTypeGet(@RequestParam Long vatTypeId) {
        try {
            VatTypeShowDTO vatType = vatTypeService.vatTypeGet(vatTypeId);
            return ResponseEntity.status(HttpStatus.OK).body(vatType);
        }
        catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}