package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.salary;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary.LongTermCareInsurancePension;
import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LongTermCareInsurancePensionRepository extends JpaRepository<LongTermCareInsurancePension, Long> , LongTermCareInsurancePensionRepositoryCustom{

    Optional<LongTermCareInsurancePension> findFirstByEndDateIsNull();
}
