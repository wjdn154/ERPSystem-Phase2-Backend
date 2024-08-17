package com.megazone.ERPSystem_phase2_Backend.financial.controller.general_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.dto.UnresolvedVoucherEntryDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.dto.UnresolvedVoucherDeleteDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.dto.UnresolvedVoucherShowAllDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.dto.UnresolvedVoucherShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.UnresolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.unresolvedVoucher.UnresolvedVoucherRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.general_voucher_entry.UnresolvedVoucherEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private final UnresolvedVoucherRepository unresolvedVoucherRepository;

    /**
     * 미결전표 등록 기능 Api
     * @param dto 사용자가 인터페이스에서 등록한 dataList객체
     * @return 정상적으로 등록되었으면, 저장된 객체 dto로 변환해 반환, 저장되지 않았으면 BAD_REQUEST 반환
     */
    @PostMapping("/api/financial/general-voucher-entry/unresolvedVoucherEntry")
    public ResponseEntity<List<UnresolvedVoucherEntryDTO>> unresolvedVoucherEntry(@RequestBody List<UnresolvedVoucherEntryDTO> dto) {

        List<UnresolvedVoucher> unresolvedVoucherList = unresolvedVoucherEntryService.unresolvedVoucherEntry(dto);

        List<UnresolvedVoucherEntryDTO> entryDtoList = new ArrayList<UnresolvedVoucherEntryDTO>();

        if(!unresolvedVoucherList.isEmpty()) {
            entryDtoList = unresolvedVoucherList.stream().map(
                            (voucher) -> {
                                return UnresolvedVoucherEntryDTO.create(voucher);
                            })
                    .toList();
        }

        return (!entryDtoList.isEmpty()) ?
                ResponseEntity.status(HttpStatus.OK).body(entryDtoList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * 해당날짜에 모든 미결전표 정보 반환 메소드
     * @param requestData 사용자가 조회할 날짜 정보
     * @return 해당 날짜조건에 만족하는 DTO 생성후 반환 ( 차변,대변 합계 / 미결전표List / 조건날짜 )
     */
    @PostMapping("api/financial/general-voucher-entry/showUnresolvedVoucher")
    public ResponseEntity<UnresolvedVoucherShowAllDTO> showUnresolvedVoucher(@RequestBody Map<String,LocalDate> requestData) {
        LocalDate date = requestData.get("searchDate");

        List<UnresolvedVoucher> unresolvedVoucherList = unresolvedVoucherEntryService.unresolvedVoucherAllSearch(date);

        List<UnresolvedVoucherShowDTO> showDtos = unresolvedVoucherList.stream().map(
                (voucher) -> { return UnresolvedVoucherShowDTO.create(voucher); }).toList();

        UnresolvedVoucherShowAllDTO showAllDto = UnresolvedVoucherShowAllDTO.create(
                date,
                showDtos,
                BigDecimal.ZERO,  // 현재잔액 기능 구현필요 <<<<
                unresolvedVoucherEntryService.totalDebit(date),
                unresolvedVoucherEntryService.totalCredit(date)
                );


        return (!unresolvedVoucherList.isEmpty()) ?
                ResponseEntity.status(HttpStatus.OK).body(showAllDto) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    /**
     * 미결전표 삭제기능
     * @param dto  사용자가 입력한 날짜, 체크한 전표번호, 담당자Id 전송객체
     * @return 삭제 완료, 미완료 정보 출력
     */
    @PostMapping("api/financial/general-voucher-entry/deleteUnresolvedVoucher")
    public ResponseEntity<String> deleteUnresolvedVoucher(@RequestBody UnresolvedVoucherDeleteDTO dto) {

        List<Long> unresolvedVoucherList = unresolvedVoucherEntryService.deleteUnresolvedVoucher(dto);

        return (!unresolvedVoucherList.isEmpty()) ?
                ResponseEntity.status(HttpStatus.OK).body("삭제가 완료되었습니다.") :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제가능한 전표가 없습니다.");
    }

}
