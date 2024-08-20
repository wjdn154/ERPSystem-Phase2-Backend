package com.megazone.ERPSystem_phase2_Backend.financial.repository.common;

import com.megazone.ERPSystem_phase2_Backend.financial.model.common.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}