package com.megazone.ERPSystem_phase2_Backend.financial.service.basic_information_management.account_subject;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.AccountSubject;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.AccountSubjectNature;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.AccountSubjectStandardFinancialStatement;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.AccountSubjectStructure;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubject.AccountSubjectRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubjectNature.AccountSubjectNatureRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubjectRelationship.AccountSubjectRelationshipRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubjectStructure.AccountSubjectStructureRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubjectCashMemo.AccountSubjectCashMemoRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubjectFixedMemo.AccountSubjectFixedMemoRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubjectStandardFinancialStatement.AccountSubjectStandardFinancialStatementRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubjectTransferMemo.AccountSubjectTransferMemoRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@SpringBootTest
@Transactional
class AccountSubjectServiceImplTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired private AccountSubjectRepository accountSubjectRepository;
    @Autowired private AccountSubjectCashMemoRepository accountSubjectCashMemoRepository;
    @Autowired private AccountSubjectFixedMemoRepository accountSubjectFixedMemoRepository;
    @Autowired private AccountSubjectNatureRepository accountSubjectNatureRepository;
    @Autowired private AccountSubjectRelationshipRepository accountSubjectRelationshipRepository;
    @Autowired private AccountSubjectStandardFinancialStatementRepository accountSubjectStandardFinancialStatementRepository;
    @Autowired private AccountSubjectStructureRepository accountSubjectStructureRepository;
    @Autowired private AccountSubjectTransferMemoRepository accountSubjectTransferMemoRepository;


    @Autowired private AccountSubjectService accountSubjectService;

    @BeforeEach
    void setUp() {
        accountSubjectRepository.deleteAll();
        accountSubjectCashMemoRepository.deleteAll();
        accountSubjectFixedMemoRepository.deleteAll();
        accountSubjectNatureRepository.deleteAll();
        accountSubjectRelationshipRepository.deleteAll();
        accountSubjectStandardFinancialStatementRepository.deleteAll();
        accountSubjectStructureRepository.deleteAll();
        accountSubjectTransferMemoRepository.deleteAll();

        AccountSubjectStructure structure = new AccountSubjectStructure();
        structure.setCode("1");
        structure.setName("당좌자산");
        structure.setMin(100);
        structure.setMax(200);
        accountSubjectStructureRepository.save(structure);

        AccountSubjectNature nature = new AccountSubjectNature();
        nature.setCode("1");
        nature.setName("예금");
        accountSubjectNatureRepository.save(nature);

        AccountSubjectStandardFinancialStatement statement = new AccountSubjectStandardFinancialStatement();
        statement.setAccountSubjectStructure(structure);
        statement.setCode("03");
        statement.setName("1.현금 및 현금성자산");
        accountSubjectStandardFinancialStatementRepository.save(statement);

        AccountSubject accountSubject = new AccountSubject();
        accountSubject.setCode("150");
        accountSubject.setName("Travel Expenses");
        accountSubject.setEnglishName("Travel Expenses");
        accountSubject.setIsActive(true);
        accountSubject.setModificationType(true);
        accountSubject.setIsForeignCurrency(false);
        accountSubject.setIsBusinessCar(false);
        accountSubject.setStructure(structure);
        accountSubject.setStandardFinancialStatement(statement);
        accountSubject.setNature(nature);
        accountSubjectRepository.save(accountSubject);
    }

    @Test
    public void saveAndFindTest() {
        // Given

        // When
        AccountSubject savedAccountSubject = accountSubjectRepository.findByCode("150").orElseThrow();

        // Then
        assertNotNull("저장된 계정과목은 null이 아니어야 함", savedAccountSubject);
        assertEquals("150", savedAccountSubject.getCode(), "저장된 계정과목의 코드는 제공된 값과 일치해야 함");
    }

    @Test
    public void updateAccount() {
        // Given
        AccountSubject findAccountSubject = accountSubjectRepository.findByCode("150").orElseThrow();
        findAccountSubject.setName("수정");

        // When
        AccountSubject updatedAccountSubject = accountSubjectService.updateAccount(findAccountSubject);

        // Then
        assertNotNull("업데이트된 계정과목은 null이 아니어야 함", updatedAccountSubject);
        assertEquals("수정", updatedAccountSubject.getName(), "계정과목의 이름이 업데이트되어야 함");
        assertEquals("150", updatedAccountSubject.getCode(), "계정과목의 코드는 변경되지 않아야 함");
    }

    @Test
    public void deleteAccount() {
        // Given
        Long accountId = 1L;  // 삭제할 계정과목의 ID

        // When
        accountSubjectService.deleteAccount(accountId);

        // Then
        Optional<AccountSubject> deletedAccount = accountSubjectRepository.findById(accountId);
        assertFalse(deletedAccount.isPresent(), "계정과목은 데이터베이스에서 삭제되어야 함");
    }

}