package com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.process_routing.RoutingStep;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.ProcessRouting;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.RoutingStep;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.RoutingStepId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoutingStepRepository extends JpaRepository<RoutingStep, RoutingStepId>, RoutingStepRepositoryCustom {

    @Query("SELECT rs FROM process_routing_routing_step rs WHERE rs.id.processRoutingId = :processRoutingId")
    List<RoutingStep> findAllByProcessRoutingIdWithDetails(@Param("processRoutingId") Long processRoutingId);

    void deleteByProcessRoutingId(Long processRoutingId);
}
