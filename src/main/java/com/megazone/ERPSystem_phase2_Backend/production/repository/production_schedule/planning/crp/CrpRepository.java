package com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.planning.crp;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.Crp;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.Mps;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.planning.mps.MpsRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrpRepository extends JpaRepository<Crp, Long>, CrpRepositoryCustom {
}
