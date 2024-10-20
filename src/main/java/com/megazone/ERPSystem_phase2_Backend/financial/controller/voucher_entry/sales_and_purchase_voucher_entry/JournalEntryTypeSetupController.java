package com.megazone.ERPSystem_phase2_Backend.financial.controller.voucher_entry.sales_and_purchase_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.AccountSubject;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.JournalEntryTypeSetup;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.JournalEntryTypeSetupShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.JournalEntryTypeSetupUpdateDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubjectRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.jorunalEntryTypeSetup.JournalEntryTypeSetupRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.sales_and_purchase_voucher_entry.JournalEntryTypeSetupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class JournalEntryTypeSetupController {
    private final JournalEntryTypeSetupService journalEntryTypeSetupService;
    private final JournalEntryTypeSetupRepository journalEntryTypeSetupRepository;



    @PostMapping("/api/financial/journal_entry_type_setup/show")
    public ResponseEntity<List<JournalEntryTypeSetupShowDTO>> showAll() {
        List<JournalEntryTypeSetupShowDTO> journalEntryTypeSetupShowDTOS = journalEntryTypeSetupRepository.findAll()
                .stream().map(JournalEntryTypeSetupShowDTO::create).toList();

        return !journalEntryTypeSetupShowDTOS.isEmpty() ?
                ResponseEntity.status(HttpStatus.OK).body(journalEntryTypeSetupShowDTOS) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @PostMapping("/api/financial/journal_entry_type_setup/update")
    public ResponseEntity<String> updateEntrySetup(@RequestBody List<JournalEntryTypeSetupUpdateDTO> dto) {
        try {
            String message = journalEntryTypeSetupService.updateEntryTypeSetup(dto);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
