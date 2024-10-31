package com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.materialProduct;

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.MaterialData;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.MaterialProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialProductRepository extends JpaRepository<MaterialProduct, Long>, MaterialProductRepositoryCustom {

    void deleteAllByMaterialData(MaterialData materialData);
}
