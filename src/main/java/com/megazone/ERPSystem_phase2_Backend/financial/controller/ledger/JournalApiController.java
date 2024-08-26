package com.megazone.ERPSystem_phase2_Backend.financial.controller.ledger;


import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.JournalDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.JournalShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.ResolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.ResolvedVoucherShowAllDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.ResolvedVoucherShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.service.ledger.JournalService;
import com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.general_voucher_entry.ResolvedVoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class JournalApiController {
    private final JournalService journalService;

    @PostMapping("/test")
    public ResponseEntity<JournalShowDTO> journalShow(@RequestBody JournalDTO dto) {
        List<ResolvedVoucherShowDTO> ShowDTOs = journalService.journalSearch(dto.getStartDate(),dto.getEndDate())
                .stream().map(ResolvedVoucherShowDTO::create).toList();

        List<BigDecimal> totalAmounts = journalService.journalTotalAmount(dto.getStartDate(),dto.getEndDate());
        BigDecimal totalCount = journalService.journalTotalCount(dto.getStartDate(),dto.getEndDate());

        JournalShowDTO journalShowDTO = JournalShowDTO.create(ShowDTOs,totalAmounts.get(0),totalAmounts.get(1),totalCount);

        return journalShowDTO != null ?
                ResponseEntity.status(HttpStatus.OK).body(journalShowDTO) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }
}
