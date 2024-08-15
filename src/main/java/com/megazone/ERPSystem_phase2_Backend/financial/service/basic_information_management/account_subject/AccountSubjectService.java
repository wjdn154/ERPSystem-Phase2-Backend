package com.megazone.ERPSystem_phase2_Backend.financial.service.basic_information_management.account_subject;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.AccountSubject;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto.AccountSubjectDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto.AccountSubjectDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto.AccountSubjectsAndMemosDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company_registration.Representative;

import java.util.List;
import java.util.Optional;

public interface AccountSubjectService {
    Optional<AccountSubjectsAndMemosDTO> findAllAccountSubjectDetails();

    void addMemoToAccountSubject (String code, String MemoType, String content);

    Optional<AccountSubjectDTO> updateAccountSubject(String code, AccountSubjectDTO dto);

    Optional<AccountSubjectDTO> saveAccountSubject(AccountSubjectDTO dto);

    void deleteAccount(String code);

}