package com.megazone.ERPSystem_phase2_Backend.financial.service.basic_information_management;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company_registration.Representative;

import java.util.List;

public interface CompanyRegistrationService {
    /**
     * 이름으로 대표자 검색 메서드
     * @param name 대표자의 이름
     * @return 해당 이름을 가진 Representative 리스트
     */
    List<Representative> findByName(String name);

    /**
     * 대표자 저장 메서드
     * @param representative 저장할 대표자 객체
     * @return 저장된 Representative 객체
     */
    Representative save(Representative representative);

    /**
     * ID로 대표자 검색 메서드
     * @param id 대표자의 ID
     * @return 해당 ID를 가진 Representative 객체
     */
    Representative findById(Long id);

    /**
     * ID로 대표자 삭제 메서드
     *
     * @param id 삭제할 대표자의 ID
     */
    void deleteById(Long id);
}