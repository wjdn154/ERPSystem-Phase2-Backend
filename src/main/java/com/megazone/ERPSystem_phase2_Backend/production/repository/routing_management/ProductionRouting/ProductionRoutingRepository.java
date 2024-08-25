package com.megazone.ERPSystem_phase2_Backend.production.repository.routing_management.ProductionRouting;

import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProductionRouting;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto.ProductionRoutingDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductionRoutingRepository extends JpaRepository<ProductionRouting, Long>, ProductionRoutingRepositoryCustom {

    Optional<ProductionRouting> findById(Long id);

    boolean existsByCode(String code);

    // 코드로 필터링
    List<ProductionRouting> findByCode(String code);

    // 코드, 이름, 활성 상태로 필터링하여 검색
    List<ProductionRouting> findByCodeContainingAndNameContainingAndIsActive(String code, String name, Boolean isActive);

    // 코드와 이름만 사용한 검색 (isActive 조건 생략)
    List<ProductionRouting> findByCodeContainingAndNameContaining(String code, String name);

    // 코드만 사용한 검색
    List<ProductionRouting> findByCodeContaining(String code);

    // 이름만 사용한 검색
    List<ProductionRouting> findByNameContaining(String name);

    // 활성 상태만 사용한 검색
    List<ProductionRouting> findByIsActive(Boolean isActive);


}
