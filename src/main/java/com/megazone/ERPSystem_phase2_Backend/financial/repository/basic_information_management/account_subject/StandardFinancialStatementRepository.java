package com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.StandardFinancialStatement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StandardFinancialStatementRepository extends JpaRepository<StandardFinancialStatement, Long> {
}