package com.megazone.ERPSystem_phase2_Backend.financial.controller.voucher_entry.sales_and_purchase_voucher_entry;


import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.UnresolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.UnresolvedSaleAndPurchaseVoucherEntryDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.sales_and_purchase_voucher_entry.UnresolvedSaleAndPurchaseVoucherService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UnresolvedSaleAndPurchaseVoucherApiController {

    private final UnresolvedSaleAndPurchaseVoucherService unresolvedSaleAndPurchaseVoucherService;

    @PostMapping("/api/financial/sale-and-purchase-voucher/entry")
    public ResponseEntity<String> Entry(@RequestBody UnresolvedSaleAndPurchaseVoucherEntryDTO dto) {
        UnresolvedSaleAndPurchaseVoucher unresolvedSaleAndPurchaseVoucher =
                unresolvedSaleAndPurchaseVoucherService.save(dto);
        System.out.println(unresolvedSaleAndPurchaseVoucher);
        return unresolvedSaleAndPurchaseVoucher != null ?
                ResponseEntity.status(HttpStatus.OK).body("등록이 완료되었습니다.") :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
