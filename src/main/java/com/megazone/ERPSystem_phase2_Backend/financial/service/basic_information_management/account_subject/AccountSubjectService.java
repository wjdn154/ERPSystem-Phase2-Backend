package com.megazone.ERPSystem_phase2_Backend.financial.service.basic_information_management.account_subject;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto.AccountSubjectDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto.AccountSubjectsAndMemosDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto.CashMemoDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto.MemoRequestDTO;

import java.util.Objects;
import java.util.Optional;

public interface AccountSubjectService {
    Optional<AccountSubjectsAndMemosDTO> findAllAccountSubjectDetails();

    Optional<Object> addMemoToAccountSubject (String accountSubjectCode, MemoRequestDTO memoRequestDTO);

    Optional<AccountSubjectDTO> updateAccountSubject(String code, AccountSubjectDTO dto);

    Optional<AccountSubjectDTO> saveAccountSubject(AccountSubjectDTO dto);

    void deleteAccount(String code);

}