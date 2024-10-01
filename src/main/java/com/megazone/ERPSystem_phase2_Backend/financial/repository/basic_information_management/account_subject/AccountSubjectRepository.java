package com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.AccountSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// JpaRepository와 커스텀 리포지토리를 확장하는 인터페이스
public interface AccountSubjectRepository extends JpaRepository<AccountSubject, Long>, AccountSubjectRepositoryCustom {

    /**
     * 이름으로 계정과목을 조회함.
     * @param name 계정과목 이름
     * @return 해당 이름의 계정과목을 Optional로 반환함.
     */
    Optional<AccountSubject> findByName(String name);

    /**
     * 코드로 계정과목을 조회함.
     * @param code 계정과목 코드
     * @return 해당 코드의 계정과목을 Optional로 반환함.
     */
    Optional<AccountSubject> findByCode(String code);

    List<AccountSubject> findByNameOrCodeContaining(String name, String code);
}