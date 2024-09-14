package com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.sales_and_purchase_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.AccountSubject;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.JournalEntryTypeSetup;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.JournalEntryTypeSetupUpdateDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubjectRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.jorunalEntryTypeSetup.JournalEntryTypeSetupRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class JournalEntryTypeSetupServiceImpl implements JournalEntryTypeSetupService {
    private final JournalEntryTypeSetupRepository journalEntryTypeSetupRepository;
    private final AccountSubjectRepository accountSubjectRepository;


    @Override
    public String updateEntryTypeSetup(Long company_id, JournalEntryTypeSetupUpdateDTO dto) {
        JournalEntryTypeSetup journalEntryTypeSetup = journalEntryTypeSetupRepository.findById(
                dto.getJournalEntryTypeId()).orElse(null);


        AccountSubject accountSubject = accountSubjectRepository.findByCode(dto.getAccountSubjectCode()).orElse(null);

        if (journalEntryTypeSetup != null && accountSubject != null) {
            journalEntryTypeSetup.setAccountSubject(accountSubject);
            return "분개유형 설정이 완료되었습니다.";
        }


        return null;
    }
}
