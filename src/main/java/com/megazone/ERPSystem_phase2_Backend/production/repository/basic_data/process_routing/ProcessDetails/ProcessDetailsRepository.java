package com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.process_routing.ProcessDetails;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.ProcessDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProcessDetailsRepository extends JpaRepository<ProcessDetails, Long>, ProcessDetailsRepositoryCustom {
    Optional<ProcessDetails> findByCode(String code);
    Optional<ProcessDetails> findByName(String name);
    List<ProcessDetails> findByCodeContainingOrNameContaining(String code, String name);

    boolean existsByCode(String code);

    List<ProcessDetails> findByNameContaining(String name);

}
