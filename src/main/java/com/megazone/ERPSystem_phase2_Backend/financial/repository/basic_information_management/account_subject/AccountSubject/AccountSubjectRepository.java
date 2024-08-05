package com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubject;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.AccountSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountSubjectRepository extends JpaRepository<AccountSubject, Long>, AccountSubjectRepositoryCustom {
    Optional<AccountSubject> findByName(String name);
    Optional<AccountSubject> findByCode(String code);
}