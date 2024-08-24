package com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.equipment;

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.EquipmentData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EquipmentDataRepository extends JpaRepository<EquipmentData, Long> , EquipmentDataRepositoryCustom{

    boolean existsByEquipmentNum(String equipmentNum);
}
