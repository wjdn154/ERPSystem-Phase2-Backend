package com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubjectFixedMemo;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.AccountSubjectFixedMemo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountSubjectFixedMemoRepository extends JpaRepository<AccountSubjectFixedMemo, Long>, AccountSubjectFixedMemoRepositoryCustom {
}