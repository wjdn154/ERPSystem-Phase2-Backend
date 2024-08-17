package com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.Structure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StructureRepository extends JpaRepository<Structure, Long> {
    Optional<Structure> findByCode(String code);
}