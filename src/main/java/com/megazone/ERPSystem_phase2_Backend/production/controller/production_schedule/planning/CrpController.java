package com.megazone.ERPSystem_phase2_Backend.production.controller.production_schedule.planning;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.dto.CrpDTO;
import com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.planning.crp.CrpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/production/crp")
@RequiredArgsConstructor
public class CrpController {
    private final CrpService crpService;

    /**
     * CRP 생성
     */
    @PostMapping("/new")
    public ResponseEntity<CrpDTO> createCrp(@RequestBody CrpDTO dto) {
        CrpDTO createdCrp = crpService.createCrp(dto);
        return new ResponseEntity<>(createdCrp, HttpStatus.CREATED);
    }

    /**
     * 특정 CRP 조회
     */
    @PostMapping("/{id}")
    public ResponseEntity<CrpDTO> getCrpById(@PathVariable("id") Long id) {
        CrpDTO crpDto = crpService.getCrpById(id);
        return new ResponseEntity<>(crpDto, HttpStatus.OK);
    }

    /**
     * 모든 CRP 조회
     */
    @PostMapping
    public ResponseEntity<List<CrpDTO>> getAllCrps() {
        List<CrpDTO> crpList = crpService.getAllCrps();
        return new ResponseEntity<>(crpList, HttpStatus.OK);
    }

    /**
     * CRP 수정
     */
    @PostMapping("/update/{id}")
    public ResponseEntity<CrpDTO> updateCrp(@PathVariable("id") Long id, @RequestBody CrpDTO dto) {
        CrpDTO updatedCrp = crpService.updateCrp(id, dto);
        return new ResponseEntity<>(updatedCrp, HttpStatus.OK);
    }

    /**
     * CRP 삭제
     */
    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCrp(@PathVariable("id") Long id) {
        crpService.deleteCrp(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
