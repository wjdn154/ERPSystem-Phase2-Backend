package com.megazone.ERPSystem_phase2_Backend.production.controller.production_schedule.planning;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.planning.dto.MaterialInputStatusDto;
import com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.planning.mrp.MaterialInputStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/production/inputStatus")
@RequiredArgsConstructor
public class MaterialInputStatusController {

    private final MaterialInputStatusService materialInputStatusService;

    /**
     * 자재 투입 현황 등록
     */
    @PostMapping("/new")
    public ResponseEntity<MaterialInputStatusDto> createMaterialInputStatus(@RequestBody MaterialInputStatusDto dto) {
        MaterialInputStatusDto createdDto = materialInputStatusService.createMaterialInputStatus(dto);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    /**
     * 특정 자재 투입 현황 조회
     */
    @PostMapping("/{id}")
    public ResponseEntity<MaterialInputStatusDto> getMaterialInputStatusById(@PathVariable("id") Long id) {
        MaterialInputStatusDto dto = materialInputStatusService.getMaterialInputStatusById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /**
     * 모든 자재 투입 현황 조회
     */
    @PostMapping
    public ResponseEntity<List<MaterialInputStatusDto>> getAllMaterialInputStatuses() {
        List<MaterialInputStatusDto> dtoList = materialInputStatusService.getAllMaterialInputStatuses();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    /**
     * 자재 투입 현황 수정
     */
    @PostMapping("/update/{id}")
    public ResponseEntity<MaterialInputStatusDto> updateMaterialInputStatus(@PathVariable Long id, @RequestBody MaterialInputStatusDto dto) {
        MaterialInputStatusDto updatedDto = materialInputStatusService.updateMaterialInputStatus(id, dto);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    /**
     * 자재 투입 현황 삭제
     */
    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMaterialInputStatus(@PathVariable Long id) {
        materialInputStatusService.deleteMaterialInputStatus(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
