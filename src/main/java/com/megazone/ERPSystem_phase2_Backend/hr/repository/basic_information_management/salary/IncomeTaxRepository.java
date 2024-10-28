package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.salary;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.salary.IncomeTax;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeTaxRepository extends JpaRepository<IncomeTax, Long> , IncomeTaxRepositoryCustom{
}
