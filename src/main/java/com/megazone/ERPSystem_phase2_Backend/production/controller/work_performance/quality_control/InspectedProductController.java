package com.megazone.ERPSystem_phase2_Backend.production.controller.work_performance.quality_control;

import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto.InspectedProductDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto.InspectedProductListDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.quality_control.dto.InspectedProductSaveDTO;
import com.megazone.ERPSystem_phase2_Backend.production.service.work_performance.quality_control.InspectedProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/production")
public class InspectedProductController {

    private final InspectedProductService inspectedProductService;

    //모든 검사품목 리스트 조회
    @PostMapping("/inspectedProducts")
    public ResponseEntity<List<InspectedProductListDTO>> getInspectedProducts() {

        //서비스에서 검사품목 리스트 조회 가져옴
        List<InspectedProductListDTO> result = inspectedProductService.findAllInspectedProduct();

        return (result != null) ?
                ResponseEntity.status(HttpStatus.OK).body(result):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //검사 품목 상세 조회
    @PostMapping("/inspectedProduct/{id}")
    public ResponseEntity<InspectedProductDetailDTO> getInspectedProductById(@PathVariable Long id) {

        //서비스에서 검사 품목 상세 조회 가져옴
        Optional<InspectedProductDetailDTO> result = inspectedProductService.findInspectedProduct(id);

        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    //검사 품목 등록
    @PostMapping("/inspectedProduct/create")
    public ResponseEntity<InspectedProductDetailDTO> createInspectedProduct(@RequestBody InspectedProductSaveDTO dto) {

        //서비스에서 검사 품목 등록 가져옴
        Optional<InspectedProductDetailDTO> result = inspectedProductService.createInspectedProduct(dto);

        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
    //검사 품목 수정
    @PutMapping("/inspectedProduct/update/{id}")
    public ResponseEntity<InspectedProductDetailDTO> updateInspectedProduct(@PathVariable Long id, @RequestBody InspectedProductSaveDTO dto) {

        //서비스에서 검사 품목 수정 가져옴
        Optional<InspectedProductDetailDTO> result = inspectedProductService.updateInspectedProduct(id, dto);

        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    //검사 품목 삭제??
    @DeleteMapping("/inspectedProduct/delete/{id}")
    public ResponseEntity<Void> deleteInspectedProduct(@PathVariable Long id) {

        //서비스에서 해당 아이디의 검사 품목 정보 삭제
        try {
            inspectedProductService.deleteInspectedProduct(id);
            return ResponseEntity.ok().build();
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
