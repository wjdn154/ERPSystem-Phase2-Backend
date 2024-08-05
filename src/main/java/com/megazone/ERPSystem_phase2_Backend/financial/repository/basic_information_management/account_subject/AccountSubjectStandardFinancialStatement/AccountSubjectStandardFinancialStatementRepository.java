package com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubjectStandardFinancialStatement;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.AccountSubjectStandardFinancialStatement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountSubjectStandardFinancialStatementRepository extends JpaRepository<AccountSubjectStandardFinancialStatement, Long>, AccountSubjectStandardFinancialStatementRepositoryCustom {
}