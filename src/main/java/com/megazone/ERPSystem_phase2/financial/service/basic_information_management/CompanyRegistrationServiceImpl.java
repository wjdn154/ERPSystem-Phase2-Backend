package com.megazone.ERPSystem_phase2.financial.service.basic_information_management;

import com.megazone.ERPSystem_phase2.financial.model.basic_information_management.company_registration.Representative;
import com.megazone.ERPSystem_phase2.financial.repository.basic_information_management.company_registration.Representative.RepresentativeRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyRegistrationServiceImpl implements CompanyRegistrationService {

    private final RepresentativeRepository representativeRepository;

    @Autowired
    public CompanyRegistrationServiceImpl(RepresentativeRepository representativeRepository) {
        this.representativeRepository = representativeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Representative> findByName(String name) {
        return representativeRepository.findByName(name);
    }

    @Override
    @Transactional
    public Representative save(Representative representative) {
        return representativeRepository.save(representative);
    }

    @Override
    @Transactional(readOnly = true)
    public Representative findById(Long id) {
        return representativeRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        representativeRepository.deleteById(id);
    }
}