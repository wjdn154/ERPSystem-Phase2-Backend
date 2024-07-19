package com.megazone.ERPSystem_phase2.financial.repository.basic_information_management.company_registration.Representative;

import com.megazone.ERPSystem_phase2.financial.model.basic_information_management.company_registration.Representative;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepresentativeRepository extends JpaRepository<Representative, Long>, RepresentativeRepositoryCustom {
}