package com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.client;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}