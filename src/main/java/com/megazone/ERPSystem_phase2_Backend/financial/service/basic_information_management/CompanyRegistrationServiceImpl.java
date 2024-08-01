package com.megazone.ERPSystem_phase2_Backend.financial.service.basic_information_management;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company_registration.Representative;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.company_registration.Representative.RepresentativeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyRegistrationServiceImpl implements CompanyRegistrationService {

    private final RepresentativeRepository representativeRepository;

    /**
     * 이름으로 대표자 검색 메서드
     *
     * @param name 대표자의 이름
     * @return 해당 이름을 가진 Representative 리스트
     */
    @Override
    @Transactional(readOnly = true)
    public List<Representative> findByName(String name) {
        return representativeRepository.findByName(name);
    }

    /**
     * 대표자 저장 메서드
     *
     * @param representative 저장할 대표자 객체
     * @return 저장된 Representative 객체
     */
    @Override
    @Transactional
    public Representative save(Representative representative) {
        return representativeRepository.save(representative);
    }

    /**
     * ID로 대표자 검색 메서드
     *
     * @param id 대표자의 ID
     * @return 해당 ID를 가진 Representative 객체
     */
    @Override
    @Transactional(readOnly = true)
    public Representative findById(Long id) {
        return representativeRepository.findById(id).orElse(null);
    }

    /**
     * ID로 대표자 삭제 메서드
     *
     * @param id 삭제할 대표자의 ID
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        representativeRepository.deleteById(id);
    }
}