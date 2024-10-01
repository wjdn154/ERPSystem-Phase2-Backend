package com.megazone.ERPSystem_phase2_Backend.financial.controller.ledger;


import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.AccountLedgerAccListDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.AccountLedgerDetailEntryDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.AccountLedgerSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.AccountLedgerShowAllListDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.service.ledger.AccountLedgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *  계정별 원장
 */
@RestController
@RequiredArgsConstructor
public class AccountLedgerApiController {
    private final AccountLedgerService accountLedgerService;

    @PostMapping("/api/financial/ledger/accountLedger/show")
    public ResponseEntity<Object> accListShow(@RequestBody AccountLedgerSearchDTO dto) {
        List<AccountLedgerAccListDTO> accountList = accountLedgerService.getAccountLedgerAccList(dto);
        return !accountList.isEmpty() ? ResponseEntity.status(HttpStatus.OK).body(accountList) :
                ResponseEntity.status(HttpStatus.NO_CONTENT).body("조건에 해당하는 거래가 없습니다.");
    }

    @PostMapping("/api/financial/ledger/accountLedger/showDetail")
    public ResponseEntity<Object> accListShowDetail(@RequestBody AccountLedgerDetailEntryDTO dto) {
        AccountLedgerShowAllListDTO accountLedgerResult = accountLedgerService.getAccountLedgerShow(dto);
        return accountLedgerResult != null ? ResponseEntity.status(HttpStatus.OK).body(accountLedgerResult) :
                ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
