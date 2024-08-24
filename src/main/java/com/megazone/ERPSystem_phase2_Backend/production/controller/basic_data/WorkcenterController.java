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

    @GetMapping
    public ResponseEntity<List<WorkcenterDTO>> getWorkcenters(
            @RequestParam(name = "name", required = false) String name) {
        List<WorkcenterDTO> workcenterDTOs;
        if (name != null) {
            workcenterDTOs = workcenterService.findByNameContaining(name);
        } else {
            workcenterDTOs = workcenterService.findAll();
        }
        return ResponseEntity.ok(workcenterDTOs);
    }

    @GetMapping("/{code}")
    public ResponseEntity<WorkcenterDTO> getWorkcenterDetail(
            @PathVariable("code") String code) {
        Optional<WorkcenterDTO> workcenterDTO = workcenterService.findByCode(code);

        return workcenterDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/create")
    public ResponseEntity<Workcenter> saveWorkcenter(@RequestBody WorkcenterDTO workcenterDTO) {
        Workcenter savedWorkcenter = workcenterService. save(workcenterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWorkcenter);
    }

    @PutMapping("/{code}")
    public ResponseEntity<Optional<Workcenter>> updateWorkcenter(
            @PathVariable("code") String code,
            @RequestBody WorkcenterDTO workcenterDTO) {
        Optional<Workcenter> updatedWorkcenter = workcenterService.updateWorkcenter(code, workcenterDTO);
        return ResponseEntity.ok(updatedWorkcenter);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkcenter(@PathVariable("id") Long id) {
        workcenterService.deleteById(id);
        return ResponseEntity.noContent().build(); // 삭제 후 204 No Content 응답
    }

//    @DeleteMapping
//    public ResponseEntity<List<Workcenter>> deleteWorkcenters(@RequestBody List<Long> ids) {
//        List<Workcenter> deletedWorkcenters = workcenterService.deleteByIds(ids);
//        return ResponseEntity.ok(deletedWorkcenters);
//    }
}
