package com.megazone.ERPSystem_phase2_Backend.financial.service.basic_information_management.account_subject;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.AccountSubject;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.AccountSubjectStructure;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubject.AccountSubjectRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubjectNature.AccountSubjectNatureRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubjectStructure.AccountSubjectStructureRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubjectCashMemo.AccountSubjectCashMemoRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubjectFixedMemo.AccountSubjectFixedMemoRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubjectStandardFinancialStatement.AccountSubjectStandardFinancialStatementRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubjectTransferMemo.AccountSubjectTransferMemoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountSubjectServiceImpl implements AccountSubjectService {

    private final AccountSubjectRepository accountSubjectRepository;
    private final AccountSubjectCashMemoRepository accountSubjectCashMemoRepository;
    private final AccountSubjectFixedMemoRepository accountSubjectFixedMemoRepository;
    private final AccountSubjectNatureRepository accountSubjectNatureRepository;
    private final AccountSubjectStandardFinancialStatementRepository accountSubjectStandardFinancialStatementRepository;
    private final AccountSubjectStructureRepository accountSubjectStructureRepository;
    private final AccountSubjectTransferMemoRepository accountSubjectTransferMemoRepository;

    /**
     * 계정과목을 저장함.
     * 계정체계의 범위를 확인하고 범위 내에 있을 경우 저장을 진행함.
     *
     * @param accountSubject 저장할 계정과목 정보
     * @return 저장된 계정과목
     * @throws IllegalArgumentException 계정 코드가 계정 체계 범위를 벗어날 경우 발생
     */
    @Override
    public AccountSubject saveAccountSubject(AccountSubject accountSubject) {
        AccountSubjectStructure accountSubjectStructure = accountSubject.getStandardFinancialStatement().getStructure();
        int accountCode = Integer.parseInt(accountSubject.getCode());
        if (accountCode < accountSubjectStructure.getMin() || accountCode > accountSubjectStructure.getMax()) {
            throw new IllegalArgumentException("계정 코드가 계정 체계 범위를 벗어났습니다.");
        }
        return accountSubjectRepository.save(accountSubject);
    }

    /**
     * 계정과목 정보를 업데이트함.
     * 주어진 계정과목 ID에 해당하는 기존 계정과목을 찾아 정보를 업데이트함.
     *
     * @param accountSubject 업데이트할 계정과목 정보
     * @return 업데이트된 계정과목
     * @throws RuntimeException 계정과목을 찾을 수 없을 경우 발생
     */
    @Override
    public AccountSubject updateAccount(AccountSubject accountSubject) {
        // 1. 계정과목 조회.
        AccountSubject existingAccount = accountSubjectRepository.findById(accountSubject.getId()).orElseThrow(() -> new RuntimeException("아이디로 계정을 찾을 수 없습니다: " + accountSubject.getId()));

        // 2. 계정과목 필드 업데이트.
        existingAccount.setCode(accountSubject.getCode());
        existingAccount.setName(accountSubject.getName());
        existingAccount.setEnglishName(accountSubject.getEnglishName());
        existingAccount.setIsActive(accountSubject.getIsActive());
        existingAccount.setModificationType(accountSubject.getModificationType());
        existingAccount.setIsForeignCurrency(accountSubject.getIsForeignCurrency());
        existingAccount.setIsBusinessCar(accountSubject.getIsBusinessCar());

        // 3.필요한 경우 연관 테이블 업데이트.
        if (accountSubject.getStructure() != null) existingAccount.setStructure(accountSubject.getStructure());
        if (accountSubject.getStandardFinancialStatement() != null) existingAccount.setStandardFinancialStatement(accountSubject.getStandardFinancialStatement());
        if (accountSubject.getCashMemo() != null && !accountSubject.getCashMemo().isEmpty()) existingAccount.setCashMemo(accountSubject.getCashMemo());
        if (accountSubject.getTransferMemo() != null && !accountSubject.getTransferMemo().isEmpty()) existingAccount.setTransferMemo(accountSubject.getTransferMemo());
        if (accountSubject.getFixedMemo() != null && !accountSubject.getFixedMemo().isEmpty()) existingAccount.setFixedMemo(accountSubject.getFixedMemo());

        // 4. 업데이트된 계정과목 저장.
        return accountSubjectRepository.save(existingAccount);
    }

    /**
     * 주어진 ID를 가진 계정과목을 삭제함.
     *
     * @param accountId 삭제할 계정과목의 ID
     * @throws RuntimeException 지정된 ID의 계정과목을 찾을 수 없을 경우 발생
     */
    @Override
    public void deleteAccount(Long accountId) {
        AccountSubject accountSubject = accountSubjectRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("아이디로 계정을 찾을 수 없습니다: " + accountId));
        accountSubjectRepository.delete(accountSubject);
    }
}
