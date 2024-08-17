package com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto.AccountSubjectDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto.AccountSubjectDetailDTO;

import java.util.List;
import java.util.Optional;

public interface AccountSubjectRepositoryCustom {
    /**
     * 코드로 계정과목의 상세 정보를 조회함.
     * @param code 계정과목 코드
     * @return 해당 코드의 계정과목 상세 정보를 Optional로 반환함.
     */
    Optional<AccountSubjectDetailDTO> findAccountSubjectDetailByCode(String code);

    /**
     * 모든 계정과목을 조회함.
     * @return 모든 계정과목의 정보를 리스트로 반환함.
     */
    List<AccountSubjectDTO> findAllAccountSubject();
}