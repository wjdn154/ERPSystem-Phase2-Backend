package com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.company_registration.Representative;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company_registration.Representative;

import java.util.List;

public interface RepresentativeRepositoryCustom {
    /**
     * 이름으로 대표자 검색 메서드
     *
     * @param name 대표자의 이름
     * @return 해당 이름을 가진 Representative 리스트
     */
    List<Representative> findByName(String name);
}