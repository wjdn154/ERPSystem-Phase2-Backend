package com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.company;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.CorporateKind;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.Representative;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepresentativeRepository extends JpaRepository<Representative, Long> {
    Optional<Representative> findByIdNumber(String idNumber);
}