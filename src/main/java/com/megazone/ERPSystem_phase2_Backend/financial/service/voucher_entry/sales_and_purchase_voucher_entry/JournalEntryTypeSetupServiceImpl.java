package com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.sales_and_purchase_voucher_entry;

import com.megazone.ERPSystem_phase2_Backend.Integrated.model.dashboard.RecentActivity;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.dashboard.enums.ActivityType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.ModuleType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.NotificationType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.PermissionType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.repository.dashboard.RecentActivityRepository;
import com.megazone.ERPSystem_phase2_Backend.Integrated.service.notification.NotificationService;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.AccountSubject;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.JournalEntryTypeSetup;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.JournalEntryTypeSetupShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.JournalEntryTypeSetupUpdateDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubjectRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.sales_and_purchase_voucher_entry.jorunalEntryTypeSetup.JournalEntryTypeSetupRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class JournalEntryTypeSetupServiceImpl implements JournalEntryTypeSetupService {
    private final JournalEntryTypeSetupRepository journalEntryTypeSetupRepository;
    private final AccountSubjectRepository accountSubjectRepository;
    private final RecentActivityRepository recentActivityRepository;
    private final NotificationService notificationService;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<JournalEntryTypeSetupShowDTO> updateEntryTypeSetup(List<JournalEntryTypeSetupUpdateDTO> dto) {

        dto.forEach(updateDTO -> {
            // JournalEntryTypeSetup 엔티티 조회
            JournalEntryTypeSetup journalEntryTypeSetup = journalEntryTypeSetupRepository.findById(
                    updateDTO.getJournalEntryTypeId()).orElseThrow(
                    () -> new RuntimeException("해당하는 분개유형이 없습니다.")
            );

            // AccountSubject 조회
            AccountSubject accountSubject = accountSubjectRepository.findByCode(updateDTO.getAccountSubjectCode()).orElseThrow(
                    () -> new RuntimeException("해당하는 계정과목이 없습니다.")
            );

            // 해당 AccountSubject가 다른 JournalEntryTypeSetup에서 사용 중인지 확인
            boolean isAccountSubjectUsed = journalEntryTypeSetupRepository.existsByAccountSubjectAndIdNot(
                    accountSubject,
                    journalEntryTypeSetup.getId()
            );

            if (isAccountSubjectUsed) {
                throw new RuntimeException("해당 계정과목은 다른 분개 유형에서 사용 중이므로 변경할 수 없습니다.");
            }

            // AccountSubject 변경
            journalEntryTypeSetup.setAccountSubject(accountSubject);

            // 엔티티 저장
            journalEntryTypeSetupRepository.save(journalEntryTypeSetup);

            recentActivityRepository.save(RecentActivity.builder()
                    .activityDescription("분개유형 수정")
                    .activityType(ActivityType.FINANCE)
                    .activityTime(LocalDateTime.now())
                    .build());
            notificationService.createAndSendNotification(
                    ModuleType.FINANCE,
                    PermissionType.USER,
                    "분개유형 수정",
                    NotificationType.JOURNAL_ENTRY_TYPESET);
        });
        return journalEntryTypeSetupRepository.findAll().stream().map(JournalEntryTypeSetupShowDTO::create).toList();
    }
}
