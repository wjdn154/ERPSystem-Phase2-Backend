package com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.common_scheduling.production_request;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.ProductionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ProductionRequestsRepository extends JpaRepository<ProductionRequest, Long> {
    boolean existsByProductIdAndRequestDate(Long productId, LocalDate requestDate);

    List<ProductionRequest> findByProductIdAndClientId(Long productId, Long clientId);

}
