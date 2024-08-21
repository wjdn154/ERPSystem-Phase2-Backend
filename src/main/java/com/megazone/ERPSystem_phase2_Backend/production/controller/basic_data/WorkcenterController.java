package com.megazone.ERPSystem_phase2_Backend.production.controller.basic_data;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.dto.WorkcenterDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.Workcenter.WorkcenterRepository;
import com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.workcenter.WorkcenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/production/workcenters")
@RequiredArgsConstructor
public class WorkcenterController {

    private final WorkcenterService workcenterService;
    private final WorkcenterRepository workcenterRepository;

    @PostMapping
    public ResponseEntity<Workcenter> saveWorkcenter(@RequestBody WorkcenterDTO workcenterDTO) {
        Workcenter savedWorkcenter = workcenterService.save(workcenterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWorkcenter);
    }

    @PutMapping("/{code}")
    public ResponseEntity<Workcenter> updateWorkcenter(@PathVariable String code, @RequestBody WorkcenterDTO workcenterDTO) {
        Workcenter updatedWorkcenter = workcenterService.updateWorkcenter(code, workcenterDTO);
        return ResponseEntity.ok(updatedWorkcenter);
    }

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

    @DeleteMapping
    public ResponseEntity<List<Workcenter>> deleteWorkcenters(@RequestBody List<Long> ids) {
        List<Workcenter> deletedWorkcenters = workcenterService.deleteByIds(ids);
        return ResponseEntity.ok(deletedWorkcenters);
    }
}