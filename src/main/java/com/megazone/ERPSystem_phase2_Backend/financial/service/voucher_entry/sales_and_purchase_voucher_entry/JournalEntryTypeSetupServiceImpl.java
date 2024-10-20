package com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.sales_and_purchase_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.AccountSubject;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.JournalEntryTypeSetup;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.JournalEntryTypeSetupUpdateDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubjectRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.jorunalEntryTypeSetup.JournalEntryTypeSetupRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class JournalEntryTypeSetupServiceImpl implements JournalEntryTypeSetupService {
    private final JournalEntryTypeSetupRepository journalEntryTypeSetupRepository;
    private final AccountSubjectRepository accountSubjectRepository;


    @Override
    public String updateEntryTypeSetup(List<JournalEntryTypeSetupUpdateDTO> dto) {

        dto.stream().forEach(updateDTO -> {
            JournalEntryTypeSetup journalEntryTypeSetup = journalEntryTypeSetupRepository.findById(
                    updateDTO.getJournalEntryTypeId()).orElseThrow(
                    () -> new RuntimeException("해당하는 분개유형이 없습니다.")
            );
            AccountSubject accountSubject = accountSubjectRepository.findByCode(updateDTO.getAccountSubjectCode()).orElseThrow(
                    () -> new RuntimeException("해당하는 계정과목이 없습니다.")
            );
            if (journalEntryTypeSetup != null && accountSubject != null) {
                journalEntryTypeSetup.setAccountSubject(accountSubject);
            }
        });

        return "분개유형 설정이 완료되었습니다.";
    }
}
