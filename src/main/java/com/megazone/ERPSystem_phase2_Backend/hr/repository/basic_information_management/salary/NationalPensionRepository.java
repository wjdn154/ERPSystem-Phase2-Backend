package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.salary;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary.NationalPension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NationalPensionRepository extends JpaRepository<NationalPension, Long> , NationalPensionRepositoryCustom {
    Optional<NationalPension> findFirstByEndDateIsNull();
}
