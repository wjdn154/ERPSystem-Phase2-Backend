package com.megazone.ERPSystem_phase2_Backend.financial.service.basic_information_management.account_subject;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.AccountSubject;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto.AccountSubjectDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto.AccountSubjectsAndMemosDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.enums.EntryType;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.enums.IncreaseDecreaseType;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubjectRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.NatureRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.StructureRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.CashMemoRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.FixedMemoRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.StandardFinancialStatementRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.TransferMemoRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CreditCardServiceImplTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired private AccountSubjectRepository accountSubjectRepository;
    @Autowired private CashMemoRepository cashMemoRepository;
    @Autowired private FixedMemoRepository fixedMemoRepository;
    @Autowired private NatureRepository natureRepository;
    @Autowired private StandardFinancialStatementRepository standardFinancialStatementRepository;
    @Autowired private StructureRepository structureRepository;
    @Autowired private TransferMemoRepository transferMemoRepository;


    @Autowired private AccountSubjectService accountSubjectService;

    private AccountSubjectDTO sampleAccountSubject;

    @BeforeEach
    void setUp() {
        // 테스트에 사용할 샘플 계정과목을 설정
        sampleAccountSubject = new AccountSubjectDTO();
        sampleAccountSubject.setStructureCode("1");
        sampleAccountSubject.setCode("199");
        sampleAccountSubject.setName("테스트 계정과목");
        sampleAccountSubject.setNatureCode("3");
        sampleAccountSubject.setEntryType(EntryType.valueOf("DEBIT"));
        sampleAccountSubject.setIncreaseDecreaseType(IncreaseDecreaseType.valueOf("INCREASE"));
        sampleAccountSubject.setIsActive(true);
        sampleAccountSubject.setModificationType(true);
        sampleAccountSubject.setIsForeignCurrency(false);
        sampleAccountSubject.setIsBusinessCar(false);
    }

    /**
     * 계정과목을 성공적으로 저장할 수 있는지 테스트.
     */
    @Test
    void testSaveAccountSubject() {
        Optional<AccountSubjectDTO> savedAccountSubject = accountSubjectService.saveAccountSubject(sampleAccountSubject);
        assertTrue(savedAccountSubject.isPresent(), "계정과목이 성공적으로 저장되어야 함");
        assertEquals(sampleAccountSubject.getCode(), savedAccountSubject.get().getCode(), "저장된 계정과목의 코드는 일치해야 함");
    }

    /**
     * 저장된 계정과목을 성공적으로 조회할 수 있는지 테스트.
     */
    @Test
    void testFindAllAccountSubject() {
        accountSubjectService.saveAccountSubject(sampleAccountSubject);
        Optional<AccountSubjectsAndMemosDTO> accountSubjects = accountSubjectService.findAllAccountSubjectDetails();
        assertFalse(accountSubjects.isEmpty(), "계정과목 목록이 비어 있지 않아야 함");
    }

    /**
     * 기존 계정과목을 업데이트할 수 있는지 테스트.
     */
    @Test
    void testUpdateAccountSubject() {
        accountSubjectService.saveAccountSubject(sampleAccountSubject);
        sampleAccountSubject.setName("수정된 계정과목 이름");
        Optional<AccountSubjectDTO> updatedAccountSubject = accountSubjectService.updateAccountSubject(sampleAccountSubject.getCode(), sampleAccountSubject);
        assertTrue(updatedAccountSubject.isPresent(), "계정과목이 성공적으로 업데이트되어야 함");
        assertEquals("수정된 계정과목 이름", updatedAccountSubject.get().getName(), "업데이트된 이름이 일치해야 함");
    }

    /**
     * 계정과목을 성공적으로 삭제할 수 있는지 테스트.
     */
    @Test
    void testDeleteAccountSubject() {
        accountSubjectService.saveAccountSubject(sampleAccountSubject);
        accountSubjectService.deleteAccount(sampleAccountSubject.getCode());
        Optional<AccountSubject> deletedAccountSubject = accountSubjectRepository.findByCode(sampleAccountSubject.getCode());

        assertTrue(deletedAccountSubject.isEmpty(), "삭제된 계정과목은 조회되지 않아야 함");
    }
}