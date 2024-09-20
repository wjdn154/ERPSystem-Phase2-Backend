package com.megazone.ERPSystem_phase2_Backend.financial.controller.voucher_entry.sales_and_purchase_voucher_entry;


import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.UnresolvedVoucherShowAllDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.UnresolvedVoucherShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.UnresolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.*;
import com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.sales_and_purchase_voucher_entry.UnresolvedSaleAndPurchaseVoucherService;
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
public class UnresolvedSaleAndPurchaseVoucherApiController {

    private final UnresolvedSaleAndPurchaseVoucherService unresolvedSaleAndPurchaseVoucherService;
    private final UsersRepository usersRepository;

    /**
     * 미결 매출매입전표 등록 기능
     * @param dto
     * @return
     */
    @PostMapping("/api/financial/sale-and-purchase-unresolved-voucher/entry")
    public ResponseEntity<String> Entry(@RequestBody UnresolvedSaleAndPurchaseVoucherEntryDTO dto) {

        UnresolvedSaleAndPurchaseVoucher unresolvedSaleAndPurchaseVoucher =
                unresolvedSaleAndPurchaseVoucherService.save(dto);
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


    /**
     * 매출매입전표 삭제
     * 삭제시 연결되어있는 미결전표도 모두 삭제
     * @param dto
     * @return
     */
    @PostMapping("/api/financial/sale-end-purchase-unresolved-voucher/deletes")
    public ResponseEntity<String> deleteVoucher(@RequestBody UnresolvedSaleAndPurchaseVoucherDeleteDTO dto) {


        String message = unresolvedSaleAndPurchaseVoucherService.deleteVoucher(dto);

        return (message != null) ?
                ResponseEntity.status(HttpStatus.OK).body("삭제가 완료되었습니다.") :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제가능한 전표가 없습니다.");
    }

    /**
     * 선택한 매출매입전표의 분개 리스트를 출력하는 기능
     * @param voucherNumber
     * @return
     */
    @PostMapping("/api/financial/sale-end-purchase-unresolved-voucher/show/{voucherNumber}")
    public ResponseEntity<Object> showOne(@PathVariable("voucherNumber") String voucherNumber) {
        try {
            List<UnresolvedVoucher> vouchers = unresolvedSaleAndPurchaseVoucherService.searchVoucher(voucherNumber);

            if (vouchers == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("등록된 전표가 없습니다.");
            }

            List<UnresolvedVoucherShowDTO> showDTOs = vouchers.stream().map(
                    UnresolvedVoucherShowDTO::create).toList();

            UnresolvedVoucherShowAllDTO showAllDTO = UnresolvedVoucherShowAllDTO.create(
                    showDTOs.get(0).getVoucherDate(),
                    showDTOs,
                    BigDecimal.ZERO,
                    unresolvedSaleAndPurchaseVoucherService.totalDebit(vouchers),
                    unresolvedSaleAndPurchaseVoucherService.totalCredit(vouchers)
            );

            return ResponseEntity.status(HttpStatus.OK).body(showAllDTO);

        } catch (Exception e) {
            e.printStackTrace();  // 예외의 자세한 정보 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("등록된 전표가 없습니다.");
        }
    }

    /**
     * 미결 매출 매입전표 승인 처리 메소드
     * @param dto
     * @return
     */
    @PostMapping("/api/financial/sale-end-purchase-unresolved-voucher/approve")
    public ResponseEntity<String> voucherApprovalProcessing(@RequestBody UnresolvedSaleAndPurchaseVoucherApprovalDTO dto) {


        List<UnresolvedSaleAndPurchaseVoucher> unresolvedVoucherList = unresolvedSaleAndPurchaseVoucherService.ApprovalProcessing(dto);


        return (unresolvedVoucherList != null && !unresolvedVoucherList.isEmpty()) ?
                ResponseEntity.status(HttpStatus.OK).body("선택한 미결전표가 " + dto.approvalResult() + "되었습니다.") :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dto.approvalResult() + "처리 실패");
    }
}
