package com.megazone.ERPSystem_phase2_Backend.production.service.resource_data.material;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductMaterialDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto.*;

import java.util.List;
import java.util.Optional;

public interface MaterialDataService {

    //자재 리스트 조회
    List<ListMaterialDataDTO> findAllMaterial();

    //특정 자재 상세 조회
    Optional<MaterialDataShowDTO> findMaterialById(Long id);

    //자재 상세 수정
    Optional<MaterialDataShowDTO> updateMaterial(Long id, MaterialDataShowDTO dto);

    //자재 상세 등록
    Optional<MaterialDataShowDTO> createMaterial(MaterialDataShowDTO dto);

    //자재 상세 삭제
    void deleteMaterial(Long id);

    //해당 자재의 유해물질 리스트 조회
    ListHazardousMaterialDTO findAllHazardousMaterialById(Long id);

    //해당 자재의 유해물질 목록 추가
    Optional<ListHazardousMaterialDTO> addHazardousMaterial(Long id, List<HazardousMaterialDTO> hazardousMaterialDTOs);

    //해당 자재의 유해물질 목록 제거
    void deleteHazardousMaterial(Long materialId, Long hazardousMaterialId);

    //해당 자재의 품목 리스트 조회
    ListProductMaterialDTO findAllProductMaterialById(Long id);

    //해당 자재의 품목 목록 추가
    Optional<ListProductMaterialDTO> addProductMaterial(Long id, List<ProductMaterialDTO> productMaterialDTOs);

    //해당 자재의 품목 목록 제거
    void deleteProductMaterial(Long materialId, String productCode);


}
