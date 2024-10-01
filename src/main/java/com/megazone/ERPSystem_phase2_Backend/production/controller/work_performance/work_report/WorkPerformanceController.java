package com.megazone.ERPSystem_phase2_Backend.production.controller.work_performance.work_report;

import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.goods_receipt.dto.WorkPerformanceDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.goods_receipt.dto.WorkPerformanceListDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.WorkPerformance;
import com.megazone.ERPSystem_phase2_Backend.production.service.work_performance.work_report.WorkPerformanceService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/production")
public class WorkPerformanceController {

    private final WorkPerformanceService workPerformanceService;

    //작업 실적 리스트 조회
    @PostMapping("/workPerformances")
    private ResponseEntity<List<WorkPerformanceListDTO>> getWorkPerformanceList() {

        //서비스에서 작업 실적 리스트 가져옴
        List<WorkPerformanceListDTO> result = workPerformanceService.findAllWorkPerformance();

        return (result != null)?
                ResponseEntity.status(HttpStatus.OK).body(result):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //작업 실적 상세 조회
    @PostMapping("/workPerformance/{id}")
    private ResponseEntity<WorkPerformanceDetailDTO> getWorkPerformanceDetail(@PathVariable Long id) {

        //서비스에서 작업 실적 상세 정보 가져옴.
        Optional<WorkPerformanceDetailDTO> result = workPerformanceService.findWorkPerformanceById(id);

        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

    }

    //작업 실적 등록
    @PostMapping("/workPerformance/createWorkPerformance")
    public ResponseEntity<WorkPerformanceDetailDTO> createWorkPerformance(@RequestBody WorkPerformanceDetailDTO dto) {

        //서비스에서 작업 실적 상세 정보 등록함.
        Optional<WorkPerformanceDetailDTO> result = workPerformanceService.createWorkPerformance(dto);

        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    //작업 실적 수정
    @PostMapping("/workPerformance/updateWorkPerformance/{id}")
    public ResponseEntity<WorkPerformanceDetailDTO> updateWorkPerformance(@RequestBody WorkPerformanceDetailDTO dto, @PathVariable Long id) {

        //서비스에서 작업 실적 상세 정보 수정함.
        Optional<WorkPerformanceDetailDTO> result = workPerformanceService.updateWorkPerformance(id, dto);

        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

}
