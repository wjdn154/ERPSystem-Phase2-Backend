package com.megazone.ERPSystem_phase2_Backend.production.service.work_performance.quality_control;

import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto.InspectedProductDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto.InspectedProductListDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto.InspectedProductSaveDTO;

import java.util.List;
import java.util.Optional;

public interface InspectedProductService {

    //리스트 조회
    List<InspectedProductListDTO> findAllInspectedProduct();

    //상세 조회
    Optional<InspectedProductDetailDTO> findInspectedProduct(Long id);

    //상세 등록
    Optional<InspectedProductDetailDTO> createInspectedProduct(InspectedProductSaveDTO dto);

    //상세 수정
    Optional<InspectedProductDetailDTO> updateInspectedProduct(Long id, InspectedProductSaveDTO dto);

    //삭제
    void deleteInspectedProduct(Long id);
}
