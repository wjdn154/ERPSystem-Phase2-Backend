package com.megazone.ERPSystem_phase2_Backend.production.controller.basic_data.bom;


import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.bom.StandardBom;
import com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.bom.StandardBom.StandardBomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/standard-boms")
public class StandardBomController {

    private final StandardBomService standardBomService;

    @Autowired
    public StandardBomController(StandardBomService standardBomService) {
        this.standardBomService = standardBomService;
    }

    // BOM 생성
    @PostMapping
    public ResponseEntity<StandardBom> createStandardBom(@RequestBody StandardBom standardBom) {
        StandardBom createdBom = standardBomService.createStandardBom(standardBom);
        return ResponseEntity.ok(createdBom);
    }

    // 특정 BOM 조회
    @GetMapping("/{id}")
    public ResponseEntity<StandardBom> getStandardBomById(@PathVariable Long id) {
        return standardBomService.getStandardBomById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 모든 BOM 목록 조회
    @GetMapping
    public ResponseEntity<List<StandardBom>> getAllStandardBoms() {
        List<StandardBom> boms = standardBomService.getAllStandardBoms();
        return ResponseEntity.ok(boms);
    }

    // BOM 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<StandardBom> updateStandardBom(@PathVariable Long id, @RequestBody StandardBom standardBom) {
        try {
            StandardBom updatedBom = standardBomService.updateStandardBom(id, standardBom);
            return ResponseEntity.ok(updatedBom);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // BOM 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStandardBom(@PathVariable Long id) {
        standardBomService.deleteStandardBom(id);
        return ResponseEntity.noContent().build();
    }

    // BOM 정전개: 하위 BOM 조회
    @GetMapping("/forward-explosion/{parentProductId}")
    public ResponseEntity<List<StandardBom>> getChildBoms(@PathVariable Long parentProductId) {
        List<StandardBom> childBoms = standardBomService.getChildBoms(parentProductId);
        return ResponseEntity.ok(childBoms);
    }

    // BOM 역전개: 상위 BOM 조회
    @GetMapping("/backward-explosion/{childProductId}")
    public ResponseEntity<List<StandardBom>> getParentBoms(@PathVariable Long childProductId) {
        List<StandardBom> parentBoms = standardBomService.getParentBoms(childProductId);
        return ResponseEntity.ok(parentBoms);
    }
}
