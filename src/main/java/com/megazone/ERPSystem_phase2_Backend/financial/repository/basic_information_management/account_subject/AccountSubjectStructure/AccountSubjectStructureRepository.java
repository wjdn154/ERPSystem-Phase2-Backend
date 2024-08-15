package com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubjectStructure;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.Structure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountSubjectStructureRepository extends JpaRepository<Structure, Long>, AccountSubjectStructureRepositoryCustom {
    Optional<Structure> findByCode(String code);
}