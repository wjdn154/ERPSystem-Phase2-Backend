package com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.planning.mps;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.Mps;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MpsRepository extends JpaRepository<Mps, Long>, MpsRepositoryCustom {
}
