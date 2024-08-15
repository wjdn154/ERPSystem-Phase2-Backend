package com.megazone.ERPSystem_phase2_Backend.financial.controller.general_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.dto.GeneralVoucherEntryDto;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.VoucherType;
import com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.general_voucher_entry.UnresolvedVoucherEntryService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//class ApiTestClass {
//    Long num;
//    VoucherType voucherType;
//    BigDecimal bigDecimal;
//    LocalDate localDate;
//}

@RestController
@RequiredArgsConstructor
public class UnresolvedVoucherApiController {

    private final UnresolvedVoucherEntryService unresolvedVoucherEntryService;

//    /**
//     * Json 통신 타입 확인용 테스트 코드
//     * @param apiTestClass
//     * @return
//     */
//
//    @PostMapping("/valueTest")
//    public ResponseEntity<ApiTestClass> testMethod(@RequestBody ApiTestClass apiTestClass) {
//        System.out.println(apiTestClass.toString());
//
//        return (apiTestClass != null) ?
//                ResponseEntity.status(HttpStatus.OK).body(apiTestClass) :
//                ResponseEntity.status(HttpStatus.OK).build();
//    }

    @PostMapping("/api/financial/general-voucher-entry/unresolvedVoucherEntry")
    public ResponseEntity<List<UnresolvedVoucher>> unresolvedVoucherEntry(@RequestBody List<GeneralVoucherEntryDto> dto) {

        List<UnresolvedVoucher> unresolvedVoucherList = unresolvedVoucherEntryService.unresolvedVoucherEntry(dto);
        unresolvedVoucherList.stream().forEach((test) -> {
            System.out.println(test);
        });
        return (!unresolvedVoucherList.isEmpty()) ?
                ResponseEntity.status(HttpStatus.OK).body(unresolvedVoucherList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("api/financial/general-voucher-entry/showUnresolvedVoucher")
    public ResponseEntity<List<UnresolvedVoucher>> showUnresolvedVoucher(@RequestParam("searchDate") LocalDate searchDate) {
        List<UnresolvedVoucher> unresolvedVoucherList = unresolvedVoucherEntryService.unresolvedVoucherAllSearch(searchDate);

        return (!unresolvedVoucherList.isEmpty()) ?
                ResponseEntity.status(HttpStatus.OK).body(unresolvedVoucherList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    @PostMapping("api/financial/general-voucher-entry/deleteUnresolvedVoucher")
    public ResponseEntity<String> deleteUnresolvedVoucher(@RequestParam("searchDate") LocalDate searchDate,
                                                          @RequestParam("searchVoucherNumber") List<String> searchVoucherNumberList) {
        List<UnresolvedVoucher> unresolvedVoucherList = unresolvedVoucherEntryService.deleteUnresolvedVoucher(searchDate,searchVoucherNumberList);

        return (!unresolvedVoucherList.isEmpty()) ?
                ResponseEntity.status(HttpStatus.OK).body("선택한 미결전표 삭제완료") :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제할 전표가 없습니다.");
    }

}
