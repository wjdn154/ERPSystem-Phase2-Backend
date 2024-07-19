package com.megazone.ERPSystem_phase2.financial.service.basic_information_management;

import com.megazone.ERPSystem_phase2.financial.model.basic_information_management.company_registration.Representative;
import com.megazone.ERPSystem_phase2.financial.repository.basic_information_management.company_registration.Representative.RepresentativeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CompanyRegistrationService {
    List<Representative> findByName(String name);
    Representative save(Representative representative);
    Representative findById(Long id);
    void deleteById(Long id);
}
