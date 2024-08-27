package com.megazone.ERPSystem_phase2_Backend.production.controller.basic_data;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.dto.WorkcenterDTO;
import com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.workcenter.WorkcenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/production/workcenters")
@RequiredArgsConstructor
public class WorkcenterController {

    private final WorkcenterService workcenterService;

    // 1. 전체 작업장 조회
    @PostMapping
    public ResponseEntity<List<WorkcenterDTO>> getAllWorkcenters() {
        List<WorkcenterDTO> workcenterDTOs = workcenterService.findAll();
        return ResponseEntity.ok(workcenterDTOs);
    }


    // 3. 이름으로 작업장 리스트 검색 조회
    @PostMapping("/search")
    public ResponseEntity<List<WorkcenterDTO>> getWorkcentersByName(
            @RequestParam("name") String name) {
        List<WorkcenterDTO> workcenterDTOs = workcenterService.findByNameContaining(name);
        return ResponseEntity.ok(workcenterDTOs);
    }

    // 코드로 작업장 세부 정보 조회
    @PostMapping("/details/{code}")
    public ResponseEntity<WorkcenterDTO> getWorkcenterDetailByCode(
            @PathVariable("code") String code) {
        Optional<WorkcenterDTO> workcenterDTO = workcenterService.findByCode(code);

        return workcenterDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 새로운 작업장 생성
    @PostMapping("/create")
    public ResponseEntity<Workcenter> saveWorkcenter(@RequestBody WorkcenterDTO workcenterDTO) {
        Workcenter savedWorkcenter = workcenterService.save(workcenterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWorkcenter);
    }

    @PostMapping("/update/{code}")
    public ResponseEntity<WorkcenterDTO> updateWorkcenter(
            @PathVariable("code") String code,
            @RequestBody WorkcenterDTO workcenterDTO) {
        try {
            Optional<WorkcenterDTO> updatedWorkcenterDTO = workcenterService.updateByCode(code, workcenterDTO);
            if (updatedWorkcenterDTO.isPresent()) {
                return ResponseEntity.ok(updatedWorkcenterDTO.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PostMapping("/delete")
    public Optional<WorkcenterDTO> deleteWorkcenter(@RequestParam("code") String code) {
        try {
            return workcenterService.deleteByCode(code);
        } catch (IllegalArgumentException e) {
            // 코드가 없거나 사용 중인 경우에 대한 예외 처리
            throw new IllegalArgumentException("삭제할 수 없습니다. 이유: " + e.getMessage());
        } catch (Exception e) {
            // 기타 예외에 대한 처리
            throw new RuntimeException("작업장을 삭제하는 중에 예상치 못한 오류가 발생했습니다. 상세 정보: " + e.getMessage(), e);
        }
    }



//    @PostMapping("/delete/{code}")
//    public ResponseEntity<WorkcenterDTO> deleteWorkcenter(@PathVariable("code") String code) {
//        try {
//            Optional<WorkcenterDTO> deletedWorkcenterDTO = workcenterService.deleteByCode(code);
//
//            if (deletedWorkcenterDTO.isPresent()) {
//                // 삭제된 DTO 정보 로그 출력
//                System.out.println("Deleted Workcenter: " + deletedWorkcenterDTO.get());
//                return ResponseEntity.ok(deletedWorkcenterDTO.get());
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 코드로 찾은 작업장이 없으면 404 응답
//            }
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 코드로 찾은 작업장이 없으면 404 응답
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 기타 오류 발생 시 500 응답
//        }
//    }
}

//    @PostMapping("/delete/{code}")
//    public ResponseEntity<Void> deleteWorkcenter(@PathVariable("code") String code) {
//        try {
//            workcenterService.deleteByCode(code);
//            return ResponseEntity.noContent().build(); // 삭제 후 204 No Content 응답
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 코드로 찾은 작업장이 없으면 404 응답
//        }
//    }

//    @DeleteMapping
//    public ResponseEntity<List<Workcenter>> deleteWorkcenters(@RequestBody List<Long> ids) {
//        List<Workcenter> deletedWorkcenters = workcenterService.deleteByIds(ids);
//        return ResponseEntity.ok(deletedWorkcenters);
//    }

