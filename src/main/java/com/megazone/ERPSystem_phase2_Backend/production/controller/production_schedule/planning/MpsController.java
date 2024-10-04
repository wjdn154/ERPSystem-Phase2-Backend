package com.megazone.ERPSystem_phase2_Backend.production.controller.production_schedule.planning;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.dto.MpsDTO;
import com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.planning.mps.MpsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/production/mps")
@RequiredArgsConstructor
public class MpsController {
    private final MpsService mpsService;

    @PostMapping("/new")
    public ResponseEntity<MpsDTO> createMps(@RequestBody MpsDTO dto) {
        MpsDTO createdMps = mpsService.createMps(dto);
        return new ResponseEntity<>(createdMps, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<List<MpsDTO>> getAllMps() {
        List<MpsDTO> mpsList = mpsService.getAllMps();
        return new ResponseEntity<>(mpsList, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<MpsDTO> getMpsById(@PathVariable Long id) {
        MpsDTO mpsDto = mpsService.getMpsById(id);
        return new ResponseEntity<>(mpsDto, HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<MpsDTO> updateMps(@PathVariable Long id, @RequestBody MpsDTO dto) {
        MpsDTO updatedMps = mpsService.updateMps(id, dto);
        return new ResponseEntity<>(updatedMps, HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMps(@PathVariable Long id) {
        mpsService.deleteMps(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
