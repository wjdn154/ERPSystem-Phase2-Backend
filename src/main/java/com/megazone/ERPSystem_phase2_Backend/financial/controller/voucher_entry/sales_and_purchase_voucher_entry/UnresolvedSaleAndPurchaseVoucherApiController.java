package com.megazone.ERPSystem_phase2_Backend.financial.controller.voucher_entry.sales_and_purchase_voucher_entry;


import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.UnresolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.UnresolvedSaleAndPurchaseVoucherDeleteDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.UnresolvedSaleAndPurchaseVoucherEntryDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.UnresolvedSaleAndPurchaseVoucherShowAllDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.UnresolvedSaleAndPurchaseVoucherShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.sales_and_purchase_voucher_entry.UnresolvedSaleAndPurchaseVoucherService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UnresolvedSaleAndPurchaseVoucherApiController {

    private final UnresolvedSaleAndPurchaseVoucherService unresolvedSaleAndPurchaseVoucherService;

    /**
     * 미결 매출매입전표 등록 기능
     * @param dto
     * @return
     */
    @PostMapping("/api/financial/sale-and-purchase-unresolved-voucher/entry")
    public ResponseEntity<String> Entry(@RequestBody UnresolvedSaleAndPurchaseVoucherEntryDTO dto) {
        UnresolvedSaleAndPurchaseVoucher unresolvedSaleAndPurchaseVoucher =
                unresolvedSaleAndPurchaseVoucherService.save(dto);
        System.out.println(unresolvedSaleAndPurchaseVoucher);
        return unresolvedSaleAndPurchaseVoucher != null ?
                ResponseEntity.status(HttpStatus.OK).body("등록이 완료되었습니다.") :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 미결 매출매입전표 전체조회 기능
     * @param requestData
     * @return
     */
    @PostMapping("/api/financial/sale-end-purchase-unresolved-voucher/shows")
    public ResponseEntity<UnresolvedSaleAndPurchaseVoucherShowAllDTO> showAll(@RequestBody Map<String, LocalDate> requestData) {
        LocalDate date = requestData.get("searchDate");
        List<UnresolvedSaleAndPurchaseVoucher> voucherList = unresolvedSaleAndPurchaseVoucherService.searchAllVoucher(date);

        if(voucherList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<UnresolvedSaleAndPurchaseVoucherShowDTO> showDTOS = voucherList.stream().map(
                UnresolvedSaleAndPurchaseVoucherShowDTO::create).toList();

        UnresolvedSaleAndPurchaseVoucherShowAllDTO showAllDTO = UnresolvedSaleAndPurchaseVoucherShowAllDTO.create(date,showDTOS);

        return ResponseEntity.status(HttpStatus.OK).body(showAllDTO);
    }

    @PostMapping("/api/financial/sale-end-purchase-unresolved-voucher/deletes")
    public ResponseEntity<String> deleteVoucher(@RequestBody UnresolvedSaleAndPurchaseVoucherDeleteDTO dto) {

        List<Long> unresolvedVoucherList = unresolvedSaleAndPurchaseVoucherService.deleteVoucher(dto);

        return (!unresolvedVoucherList.isEmpty()) ?
                ResponseEntity.status(HttpStatus.OK).body("삭제가 완료되었습니다.") :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제가능한 전표가 없습니다.");
    }
}
