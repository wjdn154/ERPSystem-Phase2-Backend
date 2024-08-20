package com.megazone.ERPSystem_phase2_Backend.financial.repository.common;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.Address;
import com.megazone.ERPSystem_phase2_Backend.financial.model.common.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
}