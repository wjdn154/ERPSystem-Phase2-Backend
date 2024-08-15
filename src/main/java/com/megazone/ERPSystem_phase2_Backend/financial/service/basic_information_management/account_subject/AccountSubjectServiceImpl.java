package com.megazone.ERPSystem_phase2_Backend.financial.service.basic_information_management.account_subject;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.AccountSubject;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.CashMemo;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.Structure;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.TransferMemo;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto.AccountSubjectDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto.AccountSubjectDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto.AccountSubjectsAndMemosDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto.StructureDTO;
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

import java.util.List;
import java.util.Optional;

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
     * 모든 계정과목과 관련된 적요 정보를 가져옴.
     *
     * @return 모든 계정과목과 적요 정보를 담은 AccountSubjectsAndMemosDTO 객체를 반환함.
     */
    @Override
    public Optional<AccountSubjectsAndMemosDTO> findAllAccountSubjectDetails() {

        // 모든 계정과목 체계를 조회하고, DTO로 변환하여 리스트로 만듦
        List<StructureDTO> structures = accountSubjectStructureRepository.findAll().stream()
                .map(structure -> new StructureDTO(
                        structure.getCode(),
                        structure.getName(),
                        structure.getMin(),
                        structure.getMax()))
                .toList();

        // 모든 계정과목을 조회하여 리스트로 만듦
        List<AccountSubjectDTO> accountSubjects = accountSubjectRepository.findAllAccountSubject();

        // 기본적으로 첫 번째 계정과목의 상세 정보를 가져오도록 설정
        String firstAccountCode = accountSubjects.isEmpty() ? null : accountSubjects.get(0).getCode();

        // 첫 번째 계정과목의 상세 정보를 조회하거나, 리스트가 비어 있으면 null 반환
        AccountSubjectDetailDTO accountSubjectDetail = firstAccountCode != null
                ? accountSubjectRepository.findAccountSubjectDetailByCode(firstAccountCode).orElse(null)
                : null;

        // 계정과목 체계, 계정과목, 첫 번째 계정과목의 상세 정보를 담은 DTO를 반환
        return Optional.of(new AccountSubjectsAndMemosDTO(structures, accountSubjects, accountSubjectDetail));
    }

    /**
     * 계정과목에 적요를 추가함.
     *
     * @param code     계정과목 코드
     * @param memoType 적요 구분 (현금적요, 대체적요)
     * @param content  적요 내용
     * @throws RuntimeException         계정과목을 찾을 수 없는 경우 예외를 던짐
     * @throws IllegalArgumentException 잘못된 적요 타입이 입력된 경우 예외를 던짐
     */
    @Override
    public void addMemoToAccountSubject(String code, String memoType, String content) {
        // 계정과목을 코드로 조회하고, 없으면 예외를 던짐
        AccountSubject accountSubject = accountSubjectRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("계정 코드로 계정을 찾을 수 없습니다: " + code));

        // 메모 타입에 따라 적절한 메모 객체를 생성하고 저장함
        switch (memoType.toUpperCase()) {
            case "CASH":
                CashMemo cashMemo = new CashMemo();
                cashMemo.setAccountSubject(accountSubject);
                cashMemo.setContent(content);
                accountSubjectCashMemoRepository.save(cashMemo);
                break;
            case "TRANSFER":
                TransferMemo transferMemo = new TransferMemo();
                transferMemo.setAccountSubject(accountSubject);
                transferMemo.setContent(content);
                accountSubjectTransferMemoRepository.save(transferMemo);
                break;
            default:
                // 잘못된 메모 타입이 입력된 경우 예외를 던짐
                throw new IllegalArgumentException("적요 타입이 잘못되었습니다: " + memoType);
        }
    }


    /**
     * 새로운 계정과목을 저장함.
     * @param dto 저장할 계정과목의 정보가 담긴 DTO
     * @return 저장된 계정과목 정보를 담은 DTO를 Optional로 반환함.
     * @throws RuntimeException 동일한 코드가 이미 존재하거나 계정과목 체계, 부모 코드가 유효하지 않은 경우 발생함.
     */
    @Override
    public Optional<AccountSubjectDTO> saveAccountSubject(AccountSubjectDTO dto) {
        // DTO를 엔티티로 변환
        AccountSubject accountSubject = new AccountSubject();

        // 동일한 코드가 이미 존재하는지 확인
        accountSubjectRepository.findByCode(dto.getCode())
                .ifPresent(account -> {
                    throw new RuntimeException("이미 존재하는 코드입니다: " + dto.getCode());
                });

        // 계정과목 체계 코드로 조회
        Structure structure = accountSubjectStructureRepository.findByCode(dto.getStructureCode())
                .orElseThrow(() -> new RuntimeException("코드로 계정과목 체계를 찾을 수 없습니다: " + dto.getStructureCode()));
        accountSubject.setStructure(structure);

        // 계정과목 코드가 계정과목 체계의 범위 내에 있는지 검증
        int accountCode = Integer.parseInt(dto.getCode());
        if (accountCode < structure.getMin() || accountCode > structure.getMax()) {
            throw new IllegalArgumentException("계정 코드가 계정 체계의 범위를 벗어났습니다. " +
                    "코드는 " + structure.getMin() + " 이상 " + structure.getMax() + " 이하이어야 합니다.");
        }

        // 부모 계정과목 설정
        if (dto.getParentCode() != null) {
            AccountSubject parent = accountSubjectRepository.findByCode(dto.getParentCode())
                    .orElseThrow(() -> new RuntimeException("부모 코드로 계정을 찾을 수 없습니다: " + dto.getParentCode()));
            accountSubject.setParent(parent);
        }

        // 계정과목의 필드 설정
        accountSubject.setCode(dto.getCode());
        accountSubject.setName(dto.getName());
        accountSubject.setEnglishName(dto.getEnglishName());
        accountSubject.setNatureCode(dto.getNatureCode());
        accountSubject.setEntryType(dto.getEntryType());
        accountSubject.setIncreaseDecreaseType(dto.getIncreaseDecreaseType());
        accountSubject.setIsActive(dto.getIsActive());
        accountSubject.setModificationType(dto.getModificationType());
        accountSubject.setIsForeignCurrency(dto.getIsForeignCurrency());
        accountSubject.setIsBusinessCar(dto.getIsBusinessCar());

        // 엔티티 저장
        AccountSubject savedAccountSubject = accountSubjectRepository.save(accountSubject);

        // 엔티티를 DTO로 변환하여 반환
        AccountSubjectDTO accountSubjectDTO = new AccountSubjectDTO(
                savedAccountSubject.getId(),
                savedAccountSubject.getStructure().getCode(),
                savedAccountSubject.getParent() != null ? savedAccountSubject.getParent().getCode() : null,
                savedAccountSubject.getCode(),
                savedAccountSubject.getName(),
                savedAccountSubject.getEnglishName(),
                savedAccountSubject.getNatureCode(),
                savedAccountSubject.getEntryType(),
                savedAccountSubject.getIncreaseDecreaseType(),
                savedAccountSubject.getIsActive(),
                savedAccountSubject.getModificationType(),
                savedAccountSubject.getIsForeignCurrency(),
                savedAccountSubject.getIsBusinessCar()
        );

        return Optional.of(accountSubjectDTO);
    }

    /**
     * 기존 계정과목을 업데이트함.
     * @param code 업데이트할 계정과목의 ID
     * @param dto 업데이트할 계정과목의 정보가 담긴 DTO
     * @return 업데이트된 계정과목 정보를 담은 DTO를 Optional로 반환함.
     * @throws RuntimeException 계정과목 ID가 유효하지 않거나 부모 코드가 유효하지 않은 경우 발생함.
     */
    @Override
    public Optional<AccountSubjectDTO> updateAccountSubject(String code, AccountSubjectDTO dto) {
        // 기존 계정과목을 ID로 조회함
        AccountSubject accountSubject = accountSubjectRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("해당 ID로 계정과목을 찾을 수 없습니다: " + code));

        if (!dto.getModificationType()) {
            throw new RuntimeException("수정할 수 없는 계정과목입니다. 코드번호 : " + code);
        }

        // 계정과목 체계가 변경된 경우, 새로운 체계로 업데이트
        if (dto.getStructureCode() != null && !dto.getStructureCode().equals(accountSubject.getStructure().getCode())) {
            Structure structure = accountSubjectStructureRepository.findByCode(dto.getStructureCode())
                    .orElseThrow(() -> new RuntimeException("코드로 계정과목 체계를 찾을 수 없습니다: " + dto.getStructureCode()));
            accountSubject.setStructure(structure);
        }

        // 부모 계정과목이 변경된 경우, 새로운 부모로 업데이트
        if (dto.getParentCode() != null && (accountSubject.getParent() == null || !dto.getParentCode().equals(accountSubject.getParent().getCode()))) {
            AccountSubject parent = accountSubjectRepository.findByCode(dto.getParentCode())
                    .orElseThrow(() -> new RuntimeException("부모 코드로 계정을 찾을 수 없습니다: " + dto.getParentCode()));
            accountSubject.setParent(parent);
        }

        // 계정과목의 필드 업데이트
        accountSubject.setName(dto.getName());
        accountSubject.setEnglishName(dto.getEnglishName());
        accountSubject.setNatureCode(dto.getNatureCode());
        accountSubject.setEntryType(dto.getEntryType());
        accountSubject.setIncreaseDecreaseType(dto.getIncreaseDecreaseType());
        accountSubject.setIsActive(dto.getIsActive());
        accountSubject.setModificationType(dto.getModificationType());
        accountSubject.setIsForeignCurrency(dto.getIsForeignCurrency());
        accountSubject.setIsBusinessCar(dto.getIsBusinessCar());

        // 엔티티 저장
        AccountSubject updatedAccountSubject = accountSubjectRepository.save(accountSubject);

        // 엔티티를 DTO로 변환하여 반환
        AccountSubjectDTO accountSubjectDTO = new AccountSubjectDTO(
                updatedAccountSubject.getId(),
                updatedAccountSubject.getStructure().getCode(),
                updatedAccountSubject.getParent() != null ? updatedAccountSubject.getParent().getCode() : null,
                updatedAccountSubject.getCode(),
                updatedAccountSubject.getName(),
                updatedAccountSubject.getEnglishName(),
                updatedAccountSubject.getNatureCode(),
                updatedAccountSubject.getEntryType(),
                updatedAccountSubject.getIncreaseDecreaseType(),
                updatedAccountSubject.getIsActive(),
                updatedAccountSubject.getModificationType(),
                updatedAccountSubject.getIsForeignCurrency(),
                updatedAccountSubject.getIsBusinessCar()
        );

        return Optional.of(accountSubjectDTO);
    }

    /**
     * 주어진 코드에 해당하는 계정과목을 삭제함.
     * @param code 삭제할 계정과목의 코드
     * @throws RuntimeException 지정된 코드의 계정과목을 찾을 수 없을 경우 발생
     */
    @Override
    public void deleteAccount(String code) {
        // 삭제할 계정과목을 조회함
        AccountSubject accountSubject = accountSubjectRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("해당 코드로 계정과목을 찾을 수 없습니다: " + code));

        if(!accountSubject.getModificationType()) {
            throw new RuntimeException("삭제할 수 없는 계정과목입니다. 코드번호 : " + code);
        }

        // 계정과목을 삭제함
        accountSubjectRepository.delete(accountSubject);
    }
}