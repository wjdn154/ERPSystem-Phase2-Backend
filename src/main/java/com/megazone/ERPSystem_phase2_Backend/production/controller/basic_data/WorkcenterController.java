package com.megazone.ERPSystem_phase2_Backend.production.controller.basic_data;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.dto.WorkcenterDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.Workcenter.WorkcenterRepository;
import com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.workcenter.WorkcenterRegistrationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workcenters")
@RequiredArgsConstructor
public class WorkcenterController {

    private final WorkcenterRegistrationServiceImpl workcenterService;
    private final WorkcenterRepository workcenterRepository;

    /**
     * 작업장 저장
     * @param workcenterDTO 저장할 작업장 정보
     * @return 저장된 작업장 객체
     */
    @PostMapping
    public ResponseEntity<Workcenter> saveWorkcenter(@RequestBody WorkcenterDTO workcenterDTO) {
        Workcenter workcenter = new Workcenter();
        // DTO에서 Workcenter 엔티티로 변환 (필요한 필드만 변환)
        workcenter.setCode(workcenterDTO.getCode());
        workcenter.setName(workcenterDTO.getName());
        workcenter.setWorkcenterType(workcenterDTO.getWorkcenterType());
        workcenter.setDescription(workcenterDTO.getDescription());
        workcenter.setIsActive(workcenterDTO.getIsActive());

        Workcenter savedWorkcenter = workcenterService.save(workcenter);
        return ResponseEntity.ok(savedWorkcenter);
    }

    /**
     * 특정 작업장 업데이트
     * @param code 업데이트할 작업장 코드
     * @param workcenterDTO 업데이트할 정보
     * @return 업데이트된 작업장 객체
     */
    @PutMapping("/{code}")
    public ResponseEntity<Workcenter> updateWorkcenter(@PathVariable String code, @RequestBody WorkcenterDTO workcenterDTO) {
        Workcenter updatedWorkcenter = workcenterService.updateWorkcenter(code, workcenterDTO);
        return ResponseEntity.ok(updatedWorkcenter);
    }

    /**
     * 작업장 목록 조회
     * @param name (선택적) 필터링할 작업장명
     * @return 작업장 리스트
     */
    @GetMapping
    public ResponseEntity<List<Workcenter>> getWorkcenters(@RequestParam(required = false) String name) {
        List<Workcenter> workcenters;
        if (name != null) {
            workcenters = workcenterRepository.findByNameContaining(name);
        } else {
            workcenters = workcenterRepository.findAll();
        }
        return ResponseEntity.ok(workcenters);
    }

    /**
     * 작업장 삭제
     * @param ids 삭제할 작업장 ID 목록
     * @return 삭제된 작업장 리스트
     */
    @DeleteMapping
    public ResponseEntity<List<Workcenter>> deleteWorkcenters(@RequestBody List<Long> ids) {
        List<Workcenter> deletedWorkcenters = workcenterService.deleteByIds(ids);
        return ResponseEntity.ok(deletedWorkcenters);
    }
}
