package com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.planning.mps;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.Mps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface MpsRepository extends JpaRepository<Mps, Long>, MpsRepositoryCustom {
    @Query("SELECT m FROM plan_of_production_mps m WHERE :date BETWEEN m.startDate AND m.endDate")
    List<Mps> searchMps(LocalDate date);
}
