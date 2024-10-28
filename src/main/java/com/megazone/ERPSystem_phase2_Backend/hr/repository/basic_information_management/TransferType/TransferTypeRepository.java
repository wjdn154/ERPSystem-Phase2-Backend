package com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.TransferType;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.TransferType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferTypeRepository extends JpaRepository<TransferType, Long>, TransferTypeRepositoryCustom {
}
