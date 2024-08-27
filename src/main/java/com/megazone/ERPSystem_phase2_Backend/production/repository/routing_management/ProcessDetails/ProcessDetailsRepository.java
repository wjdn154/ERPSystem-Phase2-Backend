package com.megazone.ERPSystem_phase2_Backend.production.repository.routing_management.ProcessDetails;

import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProcessDetails;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProcessDetailsRepository extends JpaRepository<ProcessDetails, Long>, ProcessDetailsRepositoryCustom {
    Optional<ProcessDetails> findByCode(String code);
    Optional<ProcessDetails> findByName(String name);
    List<ProcessDetails> findByCodeContainingOrNameContaining(String code, String name);

    boolean existsByCode(String code);

}
