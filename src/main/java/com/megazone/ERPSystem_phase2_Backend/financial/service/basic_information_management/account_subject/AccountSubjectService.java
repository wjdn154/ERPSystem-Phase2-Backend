package com.megazone.ERPSystem_phase2_Backend.financial.service.basic_information_management.account_subject;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.AccountSubject;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company_registration.Representative;

import java.util.List;

public interface AccountSubjectService {
    AccountSubject saveAccount(AccountSubject account);

    AccountSubject updateAccount(AccountSubject accountSubject);

    void deleteAccount(Long accountId);
}