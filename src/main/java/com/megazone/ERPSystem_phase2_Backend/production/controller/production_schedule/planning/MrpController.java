package com.megazone.ERPSystem_phase2_Backend.production.controller.production_schedule.planning;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.dto.MrpDTO;
import com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.planning.mrp.MrpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/production/mrp")
@RequiredArgsConstructor
public class MrpController {
    private final MrpService mrpService;

    /**
     * MRP 생성
     */
    @PostMapping("/new")
    public ResponseEntity<MrpDTO> createMrp(@RequestBody MrpDTO mrpDto) {
        MrpDTO createdMrp = mrpService.createMrp(mrpDto);
        return new ResponseEntity<>(createdMrp, HttpStatus.CREATED);
    }

    /**
     * 특정 MRP 조회
     */
    @PostMapping("/{id}")
    public ResponseEntity<MrpDTO> getMrpById(@PathVariable("id") Long id) {
        MrpDTO mrpDto = mrpService.getMrpById(id);
        return new ResponseEntity<>(mrpDto, HttpStatus.OK);
    }

    /**
     * 모든 MRP 조회
     */
    @PostMapping
    public ResponseEntity<List<MrpDTO>> getAllMrps() {
        List<MrpDTO> mrpList = mrpService.getAllMrps();
        return new ResponseEntity<>(mrpList, HttpStatus.OK);
    }

    /**
     * MRP 수정
     */
    @PostMapping("/update/{id}")
    public ResponseEntity<MrpDTO> updateMrp(@PathVariable("id") Long id, @RequestBody MrpDTO mrpDto) {
        MrpDTO updatedMrp = mrpService.updateMrp(id, mrpDto);
        return new ResponseEntity<>(updatedMrp, HttpStatus.OK);
    }

    /**
     * MRP 삭제
     */
    @PostMapping("delete/{id}")
    public ResponseEntity<Void> deleteMrp(@PathVariable("id") Long id) {
        mrpService.deleteMrp(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
