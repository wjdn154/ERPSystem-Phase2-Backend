package com.megazone.ERPSystem_phase2_Backend.financial.controller.voucher_entry.sales_and_purchase_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.UnresolvedVoucherShowAllDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.UnresolvedVoucherShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.ResolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.UnresolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.UnresolvedSaleAndPurchaseVoucherDeleteDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.UnresolvedSaleAndPurchaseVoucherShowAllDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.UnresolvedSaleAndPurchaseVoucherShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.sales_and_purchase_voucher_entry.ResolvedSaleAndPurchaseVoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ResolvedSaleAndPurchaseVoucherApiController {
    private final ResolvedSaleAndPurchaseVoucherService resolvedSaleAndPurchaseVoucherService;
//
//    /**
//     * 매출매입전표 전체조회 기능
//     * @param requestData
//     * @return
//     */
//    @PostMapping("/api/financial/sale-end-purchase-resolved-voucher/shows")
//    public ResponseEntity<UnresolvedSaleAndPurchaseVoucherShowAllDTO> showAll(@RequestBody Map<String, LocalDate> requestData) {
//        LocalDate date = requestData.get("searchDate");
//        List<ResolvedSaleAndPurchaseVoucher> voucherList = ResolvedSaleAndPurchaseVoucherService.searchAllVoucher(date);
//
//        if(voucherList.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//        List<UnresolvedSaleAndPurchaseVoucherShowDTO> showDTOS = voucherList.stream().map(
//                UnresolvedSaleAndPurchaseVoucherShowDTO::create).toList();
//
//        UnresolvedSaleAndPurchaseVoucherShowAllDTO showAllDTO = UnresolvedSaleAndPurchaseVoucherShowAllDTO.create(date,showDTOS);
//
//        return ResponseEntity.status(HttpStatus.OK).body(showAllDTO);
//    }
//
//
//    /**
//     * 매출매입전표 삭제
//     * 삭제시 연결되어있는 미결전표도 모두 삭제
//     * @param dto
//     * @return
//     */
//    @PostMapping("/api/financial/sale-end-purchase-resolved-voucher/deletes")
//    public ResponseEntity<String> deleteVoucher(@RequestBody UnresolvedSaleAndPurchaseVoucherDeleteDTO dto) {
//
//        String message = unresolvedSaleAndPurchaseVoucherService.deleteVoucher(dto);
//
//        return (message != null) ?
//                ResponseEntity.status(HttpStatus.OK).body("삭제가 완료되었습니다.") :
//                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제가능한 전표가 없습니다.");
//    }
//
//    /**
//     * 선택한 매출매입전표의 분개 리스트를 출력하는 기능
//     * @param voucherNumber
//     * @return
//     */
//    @PostMapping("/api/financial/sale-end-purchase-resolved-voucher/show/{voucherNumber}")
//    public ResponseEntity<Object> showOne(@PathVariable("voucherNumber") String voucherNumber) {
//        try {
//            List<UnresolvedVoucher> vouchers = unresolvedSaleAndPurchaseVoucherService.searchVoucher(voucherNumber);
//
//            if (vouchers == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("등록된 전표가 없습니다.");
//            }
//
//            List<UnresolvedVoucherShowDTO> showDTOs = vouchers.stream().map(
//                    UnresolvedVoucherShowDTO::create).toList();
//
//            UnresolvedVoucherShowAllDTO showAllDTO = UnresolvedVoucherShowAllDTO.create(
//                    showDTOs.get(0).getVoucherDate(),
//                    showDTOs,
//                    BigDecimal.ZERO,
//                    unresolvedSaleAndPurchaseVoucherService.totalDebit(vouchers),
//                    unresolvedSaleAndPurchaseVoucherService.totalCredit(vouchers)
//            );
//
//            return ResponseEntity.status(HttpStatus.OK).body(showAllDTO);
//
//        } catch (Exception e) {
//            e.printStackTrace();  // 예외의 자세한 정보 출력
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("등록된 전표가 없습니다.");
//        }
//    }
}
