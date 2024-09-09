package com.megazone.ERPSystem_phase2_Backend.financial.service.basic_information_management.account_subject;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.*;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto.*;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubjectRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.NatureRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.StructureRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.CashMemoRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.FixedMemoRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.StandardFinancialStatementRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.TransferMemoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountSubjectServiceImpl implements AccountSubjectService {

    private final AccountSubjectRepository accountSubjectRepository;
    private final CashMemoRepository cashMemoRepository;
    private final FixedMemoRepository fixedMemoRepository;
    private final NatureRepository natureRepository;
    private final StandardFinancialStatementRepository standardFinancialStatementRepository;
    private final StructureRepository structureRepository;
    private final TransferMemoRepository transferMemoRepository;

    /**
     * 모든 계정과목과 관련된 적요 정보를 가져옴.
     *
     * @return 모든 계정과목과 적요 정보를 담은 AccountSubjectsAndMemosDTO 객체를 반환함.
     */
    @Override
    public Optional<AccountSubjectsAndMemosDTO> findAllAccountSubjectDetails(Long company_id) {

        // 모든 계정과목 체계를 조회하고, DTO로 변환하여 리스트로 만듦
        List<StructureDTO> structures = structureRepository.findAll().stream()
                .map(structure -> new StructureDTO(
                        structure.getCode(),
                        structure.getName(),
                        structure.getMin(),
                        structure.getMax()))
                .toList();

        // 모든 계정과목을 조회하여 리스트로 만듦
        List<AccountSubjectDTO> accountSubjects = accountSubjectRepository.findAllAccountSubject(company_id);

        // 기본적으로 첫 번째 계정과목의 상세 정보를 가져오도록 설정
        String firstAccountCode = accountSubjects.isEmpty() ? null : accountSubjects.get(0).getCode();

        // 첫 번째 계정과목의 상세 정보를 조회하거나, 리스트가 비어 있으면 null 반환
        AccountSubjectDetailDTO accountSubjectDetail = firstAccountCode != null
                ? accountSubjectRepository.findAccountSubjectDetailByCode(company_id, firstAccountCode).orElse(null)
                : null;

        // 계정과목 체계, 계정과목, 첫 번째 계정과목의 상세 정보를 담은 DTO를 반환
        return Optional.of(new AccountSubjectsAndMemosDTO(structures, accountSubjects, accountSubjectDetail));
    }

    /**
     * 계정과목에 적요를 추가함.
     *
     * @param accountSubjectCode     계정과목 코드
     * @param memoRequestDTO 적요 정보 DTO
     * @throws RuntimeException         계정과목을 찾을 수 없는 경우 예외를 던짐
     * @throws IllegalArgumentException 잘못된 적요 타입이 입력된 경우 예외를 던짐
     */
    @Override
    public Optional<Object> addMemoToAccountSubject(Long company_id, String accountSubjectCode, MemoRequestDTO memoRequestDTO) {
        // 계정과목을 코드로 조회하고, 없으면 예외를 던짐
        AccountSubject accountSubject = accountSubjectRepository.findByCompanyIdAndCode(company_id, accountSubjectCode)
                .orElseThrow(() -> new RuntimeException("계정 코드로 계정을 찾을 수 없습니다: " + accountSubjectCode));

        // 메모 타입에 따라 적절한 메모 객체를 생성하고 저장함
        switch (memoRequestDTO.getMemoType().toUpperCase()) {
            case "CASH":
                CashMemo cashMemo = new CashMemo();
                cashMemo.setAccountSubject(accountSubject);
                cashMemo.setContent(memoRequestDTO.getContent());
                cashMemo.setCode(memoRequestDTO.getCode());
                CashMemo savedMemo = cashMemoRepository.save(cashMemo);
                return Optional.of(new CashMemoDTO(savedMemo.getId(), savedMemo.getCode(), savedMemo.getContent()));
            case "TRANSFER":
                TransferMemo transferMemo = new TransferMemo();
                transferMemo.setAccountSubject(accountSubject);
                transferMemo.setContent(memoRequestDTO.getContent());
                TransferMemo savedTransferMemo = transferMemoRepository.save(transferMemo);
                return Optional.of(new TransferMemoDTO(savedTransferMemo.getId(), savedTransferMemo.getCode(), savedTransferMemo.getContent()));
            default:
                // 잘못된 메모 타입이 입력된 경우 예외를 던짐
                throw new IllegalArgumentException("적요 타입이 잘못되었습니다: " + memoRequestDTO.getMemoType().toUpperCase());
        }
    }

    /**
     * 새로운 계정과목을 저장함.
     * @param dto 저장할 계정과목의 정보가 담긴 DTO
     * @return 저장된 계정과목 정보를 담은 DTO를 Optional로 반환함.
     * @throws RuntimeException 동일한 코드가 이미 존재하거나 계정과목 체계, 부모 코드가 유효하지 않은 경우 발생함.
     */
    @Override
    public Optional<AccountSubjectDTO> saveAccountSubject(Long company_id, AccountSubjectDTO dto) {
        // DTO를 엔티티로 변환
        AccountSubject accountSubject = new AccountSubject();

        // 동일한 코드가 이미 존재하는지 확인
        accountSubjectRepository.findByCompanyIdAndCode(company_id, dto.getCode())
                .ifPresent(account -> {
                    throw new RuntimeException("이미 존재하는 코드입니다: " + dto.getCode());
                });

        // 계정과목 체계 코드로 조회
        Structure structure = structureRepository.findByCode(dto.getStructureCode())
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
            AccountSubject parent = accountSubjectRepository.findByCompanyIdAndCode(company_id, dto.getParentCode())
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
        accountSubject.setCashMemo(dto.getCashMemos().stream()
                .map(memo -> {
                    CashMemo cashMemo = new CashMemo();
                    cashMemo.setAccountSubject(accountSubject);
                    cashMemo.setContent(memo.getContent());
                    cashMemo.setCode(memo.getCode());
                    cashMemoRepository.save(cashMemo);
                    return cashMemo;
                })
                .collect(Collectors.toList()));
        accountSubject.setTransferMemo(dto.getTransferMemos().stream()
                .map(memo -> {
                    TransferMemo transferMemo = new TransferMemo();
                    transferMemo.setAccountSubject(accountSubject);
                    transferMemo.setContent(memo.getContent());
                    transferMemo.setCode(memo.getCode());
                    transferMemoRepository.save(transferMemo);
                    return transferMemo;
                })
                .collect(Collectors.toList()));
        accountSubject.setFixedMemo(dto.getFixedMemos().stream()
                .map(memo -> {
                    FixedMemo fixedMemo = new FixedMemo();
                    fixedMemo.setAccountSubject(accountSubject);
                    fixedMemo.setContent(memo.getContent());
                    fixedMemo.setCode(memo.getCode());
                    fixedMemo.setCategory(memo.getCategory());
                    fixedMemoRepository.save(fixedMemo);
                    return fixedMemo;
                })
                .collect(Collectors.toList()));

        // 엔티티 저장
        AccountSubject savedAccountSubject = accountSubjectRepository.save(accountSubject);

        // 엔티티를 DTO로 변환하여 반환
        AccountSubjectDTO accountSubjectDTO = convertToDTO(savedAccountSubject);

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
    public Optional<AccountSubjectDTO> updateAccountSubject(Long company_id, String code, AccountSubjectDTO dto) {

        // 기존 계정과목을 코드로 조회함
        AccountSubject accountSubject = accountSubjectRepository.findByCompanyIdAndCode(company_id, code)
                .orElseThrow(() -> new RuntimeException("해당 회사코드와 계정과목코드로 계정과목을 찾을 수 없습니다: " + company_id + ", " + code));

        if (!dto.getModificationType()) {
            throw new RuntimeException("수정할 수 없는 계정과목입니다. 코드번호 : " + code);
        }

        // 계정과목 체계가 변경된 경우, 새로운 체계로 업데이트
        if (dto.getStructureCode() != null && !dto.getStructureCode().equals(accountSubject.getStructure().getCode())) {
            Structure structure = structureRepository.findByCode(dto.getStructureCode())
                    .orElseThrow(() -> new RuntimeException("코드로 계정과목 체계를 찾을 수 없습니다: " + dto.getStructureCode()));
            accountSubject.setStructure(structure);
        }

        // 부모 계정과목이 변경된 경우, 새로운 부모로 업데이트
        if (dto.getParentCode() != null && (accountSubject.getParent() == null || !dto.getParentCode().equals(accountSubject.getParent().getCode()))) {
            AccountSubject parent = accountSubjectRepository.findByCompanyIdAndCode(company_id, dto.getParentCode())
                    .orElseThrow(() -> new RuntimeException("부모 코드로 계정을 찾을 수 없습니다: " + dto.getParentCode()));
            accountSubject.setParent(parent);
        }

        // 계정과목의 필드 업데이트
        if(dto.getName() != null) accountSubject.setName(dto.getName());
        if(dto.getEnglishName() != null) accountSubject.setEnglishName(dto.getEnglishName());
        if(dto.getNatureCode() != null) accountSubject.setNatureCode(dto.getNatureCode());
        if(dto.getStandardFinancialStatementCode() != null) accountSubject.setStandardFinancialStatementCode(dto.getStandardFinancialStatementCode());
        if(dto.getEntryType() != null) accountSubject.setEntryType(dto.getEntryType());
        if(dto.getIncreaseDecreaseType() != null) accountSubject.setIncreaseDecreaseType(dto.getIncreaseDecreaseType());
        if(dto.getIsActive() != null) accountSubject.setIsActive(dto.getIsActive());
        if(dto.getModificationType() != null) accountSubject.setModificationType(dto.getModificationType());
        if(dto.getIsForeignCurrency() != null) accountSubject.setIsForeignCurrency(dto.getIsForeignCurrency());
        if(dto.getIsBusinessCar() != null) accountSubject.setIsBusinessCar(dto.getIsBusinessCar());

        // CashMemos 업데이트
        updateCashMemos(accountSubject, dto.getCashMemos());

        // TransferMemos 업데이트
        updateTransferMemos(accountSubject, dto.getTransferMemos());

        // FixedMemos 업데이트
        updateFixedMemos(accountSubject, dto.getFixedMemos());

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
                null,
                updatedAccountSubject.getStandardFinancialStatementCode(),
                updatedAccountSubject.getEntryType(),
                updatedAccountSubject.getIncreaseDecreaseType(),
                updatedAccountSubject.getIsActive(),
                updatedAccountSubject.getModificationType(),
                updatedAccountSubject.getIsForeignCurrency(),
                updatedAccountSubject.getIsBusinessCar(),
                updatedAccountSubject.getCashMemo().stream()
                        .map(memo -> new CashMemoDTO(memo.getCode(), memo.getContent()))
                        .collect(Collectors.toList()),
                updatedAccountSubject.getTransferMemo().stream()
                        .map(memo -> new TransferMemoDTO(memo.getCode(), memo.getContent()))
                        .collect(Collectors.toList()),
                updatedAccountSubject.getFixedMemo().stream()
                        .map(memo -> new FixedMemoDTO(memo.getCode(), memo.getCategory(), memo.getContent()))
                        .collect(Collectors.toList())
        );

        return Optional.of(accountSubjectDTO);
    }

    private void updateCashMemos(AccountSubject accountSubject, List<CashMemoDTO> cashMemoDTOs) {
        // 기존 현금적요 모두 삭제
        List<CashMemo> existingCashMemos = accountSubject.getCashMemo();
        if (existingCashMemos != null && !existingCashMemos.isEmpty()) {
            cashMemoRepository.deleteAll(existingCashMemos);
        }

        // 새로운 현금적요 등록
        List<CashMemo> newCashMemos = cashMemoDTOs.stream()
                .map(dtoMemo -> {
                    CashMemo newMemo = new CashMemo();
                    newMemo.setAccountSubject(accountSubject);
                    newMemo.setContent(dtoMemo.getContent());
                    newMemo.setCode(dtoMemo.getCode());
                    return newMemo;
                })
                .collect(Collectors.toList());

        // 계정과목에 새로운 현금적요 설정
        accountSubject.setCashMemo(newCashMemos);

        // 새로운 현금적요 저장
        cashMemoRepository.saveAll(newCashMemos);
    }

    private void updateTransferMemos(AccountSubject accountSubject, List<TransferMemoDTO> transferMemoDTOs) {
        // 기존 대체적요 모두 삭제
        List<TransferMemo> existingTransferMemos = accountSubject.getTransferMemo();
        if (existingTransferMemos != null && !existingTransferMemos.isEmpty()) {
            transferMemoRepository.deleteAll(existingTransferMemos);
        }

        // 새로운 대체적요 등록
        List<TransferMemo> newTransferMemos = transferMemoDTOs.stream()
                .map(dtoMemo -> {
                    TransferMemo newMemo = new TransferMemo();
                    newMemo.setAccountSubject(accountSubject);
                    newMemo.setContent(dtoMemo.getContent());
                    newMemo.setCode(dtoMemo.getCode());
                    return newMemo;
                })
                .collect(Collectors.toList());

        // 계정과목에 새로운 대체적요 설정
        accountSubject.setTransferMemo(newTransferMemos);

        // 새로운 대체적요 저장
        transferMemoRepository.saveAll(newTransferMemos);
    }

    private void updateFixedMemos(AccountSubject accountSubject, List<FixedMemoDTO> fixedMemoDTOs) {
        // 기존 고정적요 모두 삭제
        List<FixedMemo> existingFixedMemos = accountSubject.getFixedMemo();
        if (existingFixedMemos != null && !existingFixedMemos.isEmpty()) {
            fixedMemoRepository.deleteAll(existingFixedMemos);
        }

        // 새로운 고정적요 등록
        List<FixedMemo> newFixedMemos = fixedMemoDTOs.stream()
                .map(dtoMemo -> {
                    FixedMemo newMemo = new FixedMemo();
                    newMemo.setAccountSubject(accountSubject);
                    newMemo.setContent(dtoMemo.getContent());
                    newMemo.setCode(dtoMemo.getCode());
                    newMemo.setCategory(dtoMemo.getCategory());
                    return newMemo;
                })
                .collect(Collectors.toList());

        // 계정과목에 새로운 고정적요 설정
        accountSubject.setFixedMemo(newFixedMemos);

        // 새로운 고정적요 저장
        fixedMemoRepository.saveAll(newFixedMemos);
    }

    /**
     * 주어진 코드에 해당하는 계정과목을 삭제함.
     * @param code 삭제할 계정과목의 코드
     * @throws RuntimeException 지정된 코드의 계정과목을 찾을 수 없을 경우 발생
     */
    @Override
    public void deleteAccount(Long company_id, String code) {
        // 삭제할 계정과목을 조회함
        AccountSubject accountSubject = accountSubjectRepository.findByCompanyIdAndCode(company_id, code)
                .orElseThrow(() -> new RuntimeException("해당 코드로 계정과목을 찾을 수 없습니다: " + code));

        if(!accountSubject.getModificationType()) {
            throw new RuntimeException("삭제할 수 없는 계정과목입니다. 코드번호 : " + code);
        }

        // 계정과목을 삭제함
        accountSubjectRepository.delete(accountSubject);
    }

    private AccountSubjectDTO convertToDTO(AccountSubject accountSubject) {
        return new AccountSubjectDTO(
                accountSubject.getId(),
                accountSubject.getStructure().getCode(),
                accountSubject.getParent() != null ? accountSubject.getParent().getCode() : null,
                accountSubject.getCode(),
                accountSubject.getName(),
                accountSubject.getEnglishName(),
                accountSubject.getNatureCode(),
                null,
                accountSubject.getStandardFinancialStatementCode(),
                accountSubject.getEntryType(),
                accountSubject.getIncreaseDecreaseType(),
                accountSubject.getIsActive(),
                accountSubject.getModificationType(),
                accountSubject.getIsForeignCurrency(),
                accountSubject.getIsBusinessCar(),
                accountSubject.getCashMemo() != null ? accountSubject.getCashMemo().stream()
                        .map(memo -> new CashMemoDTO(memo.getId(), memo.getCode(), memo.getContent()))
                        .collect(Collectors.toList()) : null,
                accountSubject.getTransferMemo() != null ? accountSubject.getTransferMemo().stream()
                        .map(memo -> new TransferMemoDTO(memo.getId(), memo.getCode(), memo.getContent()))
                        .collect(Collectors.toList()) : null,
                accountSubject.getFixedMemo() != null ? accountSubject.getFixedMemo().stream()
                        .map(memo -> new FixedMemoDTO(memo.getId(), memo.getCode(), memo.getCategory(), memo.getContent()))
                        .collect(Collectors.toList()) : null
        );
    }
}