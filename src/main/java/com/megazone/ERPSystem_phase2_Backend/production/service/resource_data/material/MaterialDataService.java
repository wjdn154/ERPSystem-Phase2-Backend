package com.megazone.ERPSystem_phase2_Backend.production.service.resource_data.material;

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto.ListHazardousMaterialDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto.ListMaterialDataDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto.ListProductMaterialDTO;

import java.util.List;
import java.util.Optional;

public interface MaterialDataService {

    //자재 리스트 조회
    List<ListMaterialDataDTO> findAllMaterial(Long companyId);

    //자재 상세 수정
    Optional<ListMaterialDataDTO> updateMaterial(Long id, ListMaterialDataDTO dto);

    //자재 상세 등록
    Optional<ListMaterialDataDTO> createMaterial(Long companyId, ListMaterialDataDTO dto);

    //자재 상세 삭제
    void deleteMaterial(Long id);

    //해당 자재의 유해물질 리스트 조회
    ListHazardousMaterialDTO findAllHazardousMaterialById(Long id);

    //해당 자재의 품목 리스트 조회
    ListProductMaterialDTO findAllProductMaterialById(Long id);
}
