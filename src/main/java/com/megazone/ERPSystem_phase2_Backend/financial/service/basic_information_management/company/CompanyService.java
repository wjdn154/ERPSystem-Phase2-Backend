package com.megazone.ERPSystem_phase2_Backend.financial.service.basic_information_management.company;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.dto.*;

import java.util.Optional;

public interface CompanyService {
    Optional<CompanyDTO> saveCompany(CompanyDTO companyDTO);
    Optional<CompanyDTO> updateCompany(Long id, CompanyDTO companyDTO);
}