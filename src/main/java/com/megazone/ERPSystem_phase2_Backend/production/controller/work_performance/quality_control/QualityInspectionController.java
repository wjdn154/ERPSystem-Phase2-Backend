package com.megazone.ERPSystem_phase2_Backend.production.controller.work_performance.quality_control;

import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto.QualityInspectionDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto.QualityInspectionListDTO;
import com.megazone.ERPSystem_phase2_Backend.production.service.work_performance.quality_control.QualityInspectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/production")
public class QualityInspectionController {

    private final QualityInspectionService qualityInspectionService;

    //품질검사 리스트 전체 조회
    @PostMapping("/qualityInspections")
    public ResponseEntity<List<QualityInspectionListDTO>> getQualityInspections() {

        //서비스에서 품질 검사 리스트 전체 조회 가져옴
        List<QualityInspectionListDTO> result = qualityInspectionService.findAllQualityInspection();

        return (result != null)?
                ResponseEntity.status(HttpStatus.OK).body(result):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //품질 검사 상세 조회
    @PostMapping("/qualityInspection/{id}")
    public ResponseEntity<QualityInspectionDetailDTO> getQualityInspectionById(@PathVariable("id") Long id) {

        //서비스에서 품질 검사 상세 조회 가져옴
        Optional<QualityInspectionDetailDTO> result = qualityInspectionService.findQualityInspection(id);

        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    //품질 검사 상세 등록
    @PostMapping("/qualityInspection/createQualityInspection")
    public ResponseEntity<QualityInspectionDetailDTO> createQualityInspection(@RequestBody QualityInspectionDetailDTO dto) {

        //서비스에서 품질 검사 상세 등록
        Optional<QualityInspectionDetailDTO> result = qualityInspectionService.createQualityInspection(dto);

        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    //품질 검사 상세 수정
    @PutMapping("/qualityInspection/updateQualityInspection/{id}")
    public ResponseEntity<QualityInspectionDetailDTO> updateQualityInspection(@PathVariable("id") Long id, @RequestBody QualityInspectionDetailDTO dto) {

        //서비스에서 품질 검사 상세 수정
        Optional<QualityInspectionDetailDTO> result = qualityInspectionService.updateQualityInspection(id, dto);

        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    //품질 검사 삭제
    @DeleteMapping("/qualityInspection/deleteQualityInspection/{id}")
    public ResponseEntity<Void> deleteQualityInspection(@PathVariable("id") Long id) {

        //서비스에서 해당 아이디의 유해물질 정보를 삭제함
        try {
            qualityInspectionService.deleteQualityInspection(id);
            return ResponseEntity.ok().build();
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
