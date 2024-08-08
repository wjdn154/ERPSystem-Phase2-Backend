package com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubjectNature;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.Nature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountSubjectNatureRepository extends JpaRepository<Nature, Long>, AccountSubjectNatureRepositoryCustom {
}