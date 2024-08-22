package com.megazone.ERPSystem_phase2_Backend.production.repository.routing_management.ProductionRouting;

import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProductionRouting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductionRoutingRepository extends JpaRepository<ProductionRouting, Long>, ProductionRoutingRepositoryCustom {

    Optional<ProductionRouting> findById(Long id);

    boolean existsByCode(String code);

    // 코드로 필터링
    List<ProductionRouting> findByCode(String code);

}
