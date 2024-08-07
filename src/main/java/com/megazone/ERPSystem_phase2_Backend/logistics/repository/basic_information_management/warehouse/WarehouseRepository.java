package com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_registration.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long>, WarehouseRepositoryCustom {
    Optional<Warehouse> findByName(String name);

    Optional<Warehouse> findByCode(String code);
}
