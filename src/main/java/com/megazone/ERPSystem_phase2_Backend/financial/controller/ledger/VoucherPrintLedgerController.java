package com.megazone.ERPSystem_phase2_Backend.financial.controller.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.VoucherPrintSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.ResolvedVoucherShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.service.ledger.VoucherPrintLedgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class VoucherPrintLedgerController {
    private final VoucherPrintLedgerService voucherPrintLedgerService;


    @PostMapping("/api/financial/ledger/VoucherPrint/show")
    public ResponseEntity<Object> show(@RequestBody VoucherPrintSearchDTO dto) {
        List<ResolvedVoucherShowDTO> voucherPrintList = voucherPrintLedgerService.VoucherPrintList(dto);

//        return voucherPrintList.isEmpty() ? ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("해당하는 전표가 없습니다.") :
//                ResponseEntity.status(HttpStatus.OK).body(voucherPrintList);

        return ResponseEntity.status(HttpStatus.OK).body(voucherPrintList);
    }
}
