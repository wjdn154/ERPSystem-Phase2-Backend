package com.megazone.ERPSystem_phase2_Backend.production.controller.basic_data.bom;


import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.bom.dto.StandardBomDTO;
import com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.bom.StandardBom.StandardBomService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/production/standardBoms")
@RequiredArgsConstructor
public class StandardBomController {

    private final StandardBomService standardBomService;

    // BOM 생성
    @PostMapping("/new")
    public ResponseEntity<?> createStandardBom(@Valid @RequestBody StandardBomDTO standardBomDTO) {
        try {
            StandardBomDTO createdBom = standardBomService.createStandardBom(standardBomDTO);
            return ResponseEntity.ok(createdBom);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("서버 오류 발생: " + e.getMessage());
        }
    }

    // 특정 BOM 조회
    @PostMapping("/{id}")
    public ResponseEntity<?> getStandardBomById(@PathVariable("id") Long id) {

        try {
            StandardBomDTO bomDTO = standardBomService.getStandardBomById(id);
            return ResponseEntity.ok(bomDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }

    // 모든 BOM 목록 조회
    @PostMapping
    public ResponseEntity<?> getAllStandardBoms() {

        try {
            List<StandardBomDTO> boms = standardBomService.getAllStandardBoms();
            return ResponseEntity.ok(boms);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("서버 오류 발생: " + e.getMessage());
        }

    }

    // BOM 업데이트
    @PostMapping("update/{id}")
    public ResponseEntity<Map<String, ?>> updateStandardBom(@PathVariable("id") Long id, @RequestBody StandardBomDTO updatedBomDTO) {
        Map<String, Object> response = new HashMap<>();

        try {

            StandardBomDTO updatedBom = standardBomService.updateStandardBom(id, updatedBomDTO);
            String parentProductMessage = (updatedBom.getProductId() == null)
                    ? "등록된 상위 품목이 없습니다." : "상위 품목이 설정되었습니다.";

            response.put("message", parentProductMessage);
            response.put("data", updatedBom);

            return ResponseEntity.ok(response);

        } catch (EntityNotFoundException e) {
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (IllegalArgumentException e) {
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch(Exception e) {
            response.put("message", "서버 오류 발생: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }

    }

    // BOM 삭제
    @PostMapping("delete/{id}")
    public ResponseEntity<?> deleteStandardBom(@PathVariable("id") Long id) {
        try {
            StandardBomDTO deletedBom = standardBomService.deleteStandardBom(id);
            return ResponseEntity.ok("성공적으로 삭제되었습니다: " + deletedBom);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("서버 오류 발생: " + e.getMessage());
        }
    }

    // BOM 정전개: 하위 BOM 조회
    @PostMapping("/forward-explosion/{parentProductId}")
    public ResponseEntity<?> getChildBoms(@PathVariable("parentProductId") Long parentProductId) {

        try {
            List<StandardBomDTO> childBoms = standardBomService.getChildBoms(parentProductId, new HashSet<>());
            return ResponseEntity.ok(childBoms);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("서버 오류 발생: " + e.getMessage());
        }


    }

    @PostMapping("/backward-explosion/{childProductId}")
    public ResponseEntity<?> getParentBom(@PathVariable("childProductId") Long childProductId) {
        try {
            StandardBomDTO parentBom = standardBomService.getParentBom(childProductId);
            return ResponseEntity.ok(parentBom);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

//    // BOM 역전개: 상위 BOM 조회
//    @PostMapping("/backward-explosion/{childProductId}")
//    public ResponseEntity<?> getParentBoms(@PathVariable Long childProductId) {
//
//        try {
//            List<StandardBomDTO> parentBoms = standardBomService.getParentBoms(childProductId);
//            return ResponseEntity.ok(parentBoms);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("서버 오류 발생: " + e.getMessage());
//        }
//    }
}
