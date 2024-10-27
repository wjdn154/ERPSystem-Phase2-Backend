package com.megazone.ERPSystem_phase2_Backend.Integrated.repository;

import com.megazone.ERPSystem_phase2_Backend.Integrated.model.RecentActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecentActivityRepository extends JpaRepository<RecentActivity, Long> {
}