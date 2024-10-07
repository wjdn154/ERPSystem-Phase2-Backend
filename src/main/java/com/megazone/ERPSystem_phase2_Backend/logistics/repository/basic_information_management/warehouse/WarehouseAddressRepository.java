package com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseAddressRepository extends JpaRepository<Address, Long> {
}
