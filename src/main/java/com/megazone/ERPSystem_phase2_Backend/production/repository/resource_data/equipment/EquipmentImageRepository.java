package com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.equipment;

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.EquipmentDataImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentImageRepository extends JpaRepository<EquipmentDataImage, Long>, EquipmentImageRepositoryCustom {
}
