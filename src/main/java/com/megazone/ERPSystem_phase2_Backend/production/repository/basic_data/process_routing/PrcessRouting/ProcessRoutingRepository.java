package com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.process_routing.PrcessRouting;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.ProcessRouting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProcessRoutingRepository extends JpaRepository<ProcessRouting, Long>, ProcessRoutingRepositoryCustom {

    Optional<ProcessRouting> findById(Long id);

    boolean existsByCode(String code);

    // 코드로 필터링
    List<ProcessRouting> findByCode(String code);

    // 이름만 사용한 검색
    List<ProcessRouting> findByNameContaining(String name);

    // 활성 상태만 사용한 검색
    List<ProcessRouting> findByIsActive(Boolean isActive);

//    // 코드, 이름, 활성 상태로 필터링하여 검색
//    List<ProcessRouting> findByCodeContainingAndNameContainingAndIsActive(String code, String name, Boolean isActive);
//
//    // 코드와 이름만 사용한 검색 (isActive 조건 생략)
//    List<ProcessRouting> findByCodeContainingAndNameContaining(String code, String name);
//
//    // 코드만 사용한 검색
//    List<ProcessRouting> findByCodeContaining(String code);
}
