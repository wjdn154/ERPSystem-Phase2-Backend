package com.megazone.ERPSystem_phase2_Backend.financial.controller.voucher_entry.sales_and_purchase_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatTypeShowAllDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatTypeShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.enums.TransactionType;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.vatType.VatTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class VatTypeApiController {

    private final VatTypeRepository vatTypeRepository;


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
}