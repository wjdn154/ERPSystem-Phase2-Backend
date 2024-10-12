package com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.planning.mrp;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.mrp.MaterialInputStatus;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.mrp.Mrp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialInputStatusRepository extends JpaRepository<MaterialInputStatus, Long>, MaterialInputStatusRepositoryCustom {
}
