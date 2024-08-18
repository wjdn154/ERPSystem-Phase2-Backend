package com.megazone.ERPSystem_phase2_Backend.financial.controller.general_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.dto.*;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.ResolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.general_voucher_entry.ResolvedVoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ResolvedVoucherApiController {
    private final ResolvedVoucherService resolvedVoucherService;

    /**
     * 승인된 일반전표 조회
     * @param requestData
     * @return
     */
    @PostMapping("/api/financial/general-voucher-entry/showResolvedVoucherEntry")
    public ResponseEntity<ResolvedVoucherShowAllDTO> showAllResolvedVoucher(@RequestBody Map<String, LocalDate> requestData) {
        LocalDate date = requestData.get("searchDate");

        List<ResolvedVoucher> resolvedVoucherList = resolvedVoucherService.resolvedVoucherAllSearch(date);

        List<ResolvedVoucherShowDTO> showDtos = resolvedVoucherList.stream().map(
                (voucher) -> { return ResolvedVoucherShowDTO.create(voucher); }).toList();

        ResolvedVoucherShowAllDTO showAllDto = ResolvedVoucherShowAllDTO.create(
                date,
                showDtos,
                BigDecimal.ZERO,  // 현재잔액 기능 구현필요 <<<<
                resolvedVoucherService.totalDebit(date),
                resolvedVoucherService.totalCredit(date)
        );

        return (!resolvedVoucherList.isEmpty()) ?
                ResponseEntity.status(HttpStatus.OK).body(showAllDto) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * 일반전표 삭제 메소드
     * @param dto
     * @return
     */
    @PostMapping("api/financial/general-voucher-entry/deleteResolvedVoucher")
    public ResponseEntity<String> deleteResolvedVoucher(@RequestBody ResolvedVoucherDeleteDTO dto) {

        List<Long> resolvedVoucherList = resolvedVoucherService.deleteResolvedVoucher(dto);

        return (!resolvedVoucherList.isEmpty()) ?
                ResponseEntity.status(HttpStatus.OK).body("삭제가 완료되었습니다.") :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제가능한 전표가 없습니다.");
    }


    // 날짜와 계정과목으로 조회

}
