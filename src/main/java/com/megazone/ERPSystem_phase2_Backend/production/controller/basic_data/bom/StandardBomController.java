package com.megazone.ERPSystem_phase2_Backend.production.controller.basic_data.bom;


import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.bom.dto.StandardBomDTO;
import com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.bom.StandardBom.StandardBomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/standardBoms")
@RequiredArgsConstructor
public class StandardBomController {

    private final StandardBomService standardBomService;

    // BOM 생성
    @PostMapping("/new")
    public ResponseEntity<StandardBomDTO> createStandardBom(@Valid @RequestBody StandardBomDTO standardBomDTO) {
        StandardBomDTO createdBom = standardBomService.createStandardBom(standardBomDTO);
        return ResponseEntity.ok(createdBom);
    }

    // 특정 BOM 조회
    @PostMapping("/{id}")
    public ResponseEntity<Optional<StandardBomDTO>> getStandardBomById(@PathVariable Long id) {
        Optional<StandardBomDTO> bomDTO = standardBomService.getStandardBomById(id);
        return ResponseEntity.ok(bomDTO);
    }

    // 모든 BOM 목록 조회
    @PostMapping
    public ResponseEntity<List<StandardBomDTO>> getAllStandardBoms() {
        List<StandardBomDTO> boms = standardBomService.getAllStandardBoms();
        return ResponseEntity.ok(boms);
    }

    // BOM 업데이트
    @PostMapping("update/{id}")
    public ResponseEntity<StandardBomDTO> updateStandardBom(@PathVariable Long id, @RequestBody StandardBomDTO standardBomDTO) {
            StandardBomDTO updatedBom = standardBomService.updateStandardBom(id, standardBomDTO);
            return ResponseEntity.ok(updatedBom);
    }

    // BOM 삭제
    @PostMapping("delete/{id}")
    public ResponseEntity<Void> deleteStandardBom(@PathVariable Long id) {
        standardBomService.deleteStandardBom(id);
        return ResponseEntity.noContent().build();
    }

    // BOM 정전개: 하위 BOM 조회
    @PostMapping("/forward-explosion/{parentProductId}")
    public ResponseEntity<List<StandardBomDTO>> getChildBoms(@PathVariable Long parentProductId) {
        List<StandardBomDTO> childBoms = standardBomService.getChildBoms(parentProductId);
        return ResponseEntity.ok(childBoms);
    }

    // BOM 역전개: 상위 BOM 조회
    @PostMapping("/backward-explosion/{childProductId}")
    public ResponseEntity<List<StandardBomDTO>> getParentBoms(@PathVariable Long childProductId) {
        List<StandardBomDTO> parentBoms = standardBomService.getParentBoms(childProductId);
        return ResponseEntity.ok(parentBoms);
    }
}
