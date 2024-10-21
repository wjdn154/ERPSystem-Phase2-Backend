package com.megazone.ERPSystem_phase2_Backend.financial.controller.voucher_entry.sales_and_purchase_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.ResolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.ResolvedVoucherShowAllDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.ResolvedVoucherShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.ResolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.*;
import com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.sales_and_purchase_voucher_entry.ResolvedSaleAndPurchaseVoucherService;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    /**
     * 매출매입전표 전체조회 기능
     * @param requestData
     * @return
     */
    @PostMapping("/api/financial/sale-end-purchase-resolved-voucher/shows")
    public ResponseEntity<ResolvedSaleAndPurchaseVoucherShowAllDTO> showAll(@RequestBody Map<String, LocalDate> requestData) {

        LocalDate date = requestData.get("searchDate");

        List<ResolvedSaleAndPurchaseVoucher> voucherList = resolvedSaleAndPurchaseVoucherService.searchAllVoucher(date);

//        if(voucherList.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
        List<ResolvedSaleAndPurchaseVoucherShowDTO> showDTOS = voucherList.stream().map(
                ResolvedSaleAndPurchaseVoucherShowDTO::create).toList();

        ResolvedSaleAndPurchaseVoucherShowAllDTO showAllDTO = ResolvedSaleAndPurchaseVoucherShowAllDTO.create(date,showDTOS);

        return ResponseEntity.status(HttpStatus.OK).body(showAllDTO);
    }

        /**
     * 선택한 매출매입전표의 분개 리스트를 출력하는 기능
     * @param voucherId
     * @return
     */
    @PostMapping("/api/financial/sale-and-purchase-resolved-voucher/show/entryShow")
    public ResponseEntity<Object> showOne(@RequestBody Map<String, Long> voucherId) {
        try {
            Long searchId = voucherId.get("voucherId");
            List<ResolvedVoucher> vouchers = resolvedSaleAndPurchaseVoucherService.searchEntryVoucher(searchId);

            if (vouchers == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("등록된 전표가 없습니다.");
            }

            List<ResolvedVoucherShowDTO> showDTOs = vouchers.stream().map(
                    ResolvedVoucherShowDTO::create).toList();

            ResolvedVoucherShowAllDTO showAllDTO = ResolvedVoucherShowAllDTO.create(
                    showDTOs.get(0).getVoucherDate(),
                    showDTOs,
                    BigDecimal.ZERO,
                    resolvedSaleAndPurchaseVoucherService.totalDebit(vouchers),
                    resolvedSaleAndPurchaseVoucherService.totalCredit(vouchers)
            );

            return ResponseEntity.status(HttpStatus.OK).body(showAllDTO);

        } catch (Exception e) {
            e.printStackTrace();  // 예외의 자세한 정보 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("등록된 전표가 없습니다.");
        }
    }


    /**
     * 매출매입전표 삭제
     * 삭제시 연결되어있는 미결전표도 모두 삭제
     * @param dto
     * @return
     */
    @PostMapping("/api/financial/sale-end-purchase-resolved-voucher/deletes")
    public ResponseEntity<String> deleteVoucher(@RequestBody ResolvedSaleAndPurchaseVoucherDeleteDTO dto) {

        String message = resolvedSaleAndPurchaseVoucherService.deleteVoucher(dto);

        return (message != null) ?
                ResponseEntity.status(HttpStatus.OK).body("삭제가 완료되었습니다.") :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제가능한 전표가 없습니다.");
    }

}
