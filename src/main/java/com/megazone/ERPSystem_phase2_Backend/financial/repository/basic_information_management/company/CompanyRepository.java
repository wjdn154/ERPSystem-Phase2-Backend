package com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.company;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company_registration.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}