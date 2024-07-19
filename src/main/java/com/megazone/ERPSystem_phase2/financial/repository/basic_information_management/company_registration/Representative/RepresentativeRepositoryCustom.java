package com.megazone.ERPSystem_phase2.financial.repository.basic_information_management.company_registration.Representative;

import com.megazone.ERPSystem_phase2.financial.model.basic_information_management.company_registration.Representative;

import java.util.List;

public interface RepresentativeRepositoryCustom {
    List<Representative> findByName(String name);
}
