package com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.company;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company_registration.CorporateKind;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorporateKindRepository extends JpaRepository<CorporateKind, Long> {
}