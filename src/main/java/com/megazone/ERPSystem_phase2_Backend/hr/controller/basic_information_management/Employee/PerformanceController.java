package com.megazone.ERPSystem_phase2_Backend.hr.controller.basic_information_management.Employee;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.PerformanceCreateDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.PerformanceDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.PerformanceOneDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.PerformanceShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Performance.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr")
public class PerformanceController {

    private final PerformanceService performanceService;

    // 성과 평가 등록
    @PostMapping("/performance/save")
    public ResponseEntity<PerformanceDTO> addPerformance(@RequestBody PerformanceCreateDTO performanceCreateDTO) {
        PerformanceDTO performanceDTO = performanceService.addPerformance(performanceCreateDTO);
        return new ResponseEntity<>(performanceDTO, HttpStatus.CREATED);
    }

    // 특정 사원의 성과 평가 조회
    @PostMapping("/performance/employee/{employeeId}")
    public ResponseEntity<List<PerformanceShowDTO>> getPerformanceByEmployee(@PathVariable("employeeId") Long employeeId) {
        List<PerformanceShowDTO> performances = performanceService.getPerformanceByEmployee(employeeId);
        return new ResponseEntity<>(performances, HttpStatus.OK);
    }

    // 성과 평가 수정
    @PostMapping("/performance/put/{performanceId}")
    public ResponseEntity<PerformanceShowDTO> updatePerformance(@PathVariable("performanceId") Long performanceId, @RequestBody PerformanceOneDTO performanceOneDTO) {
        if (performanceId == null) {
            throw new IllegalArgumentException("잘못된 performanceId 값입니다.");
        }
        PerformanceShowDTO performanceShowDTO = performanceService.updatePerformance(performanceId, performanceOneDTO);
        return new ResponseEntity<>(performanceShowDTO, HttpStatus.OK);
    }

    // 성과 평가 삭제
    @PostMapping("/performance/del/{performanceId}")
    public ResponseEntity<String> deletePerformance(@PathVariable("performanceId") Long performanceId) {
        // 서비스 호출하여 삭제 및 성공 메시지 반환
        String message = performanceService.deletePerformance(performanceId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // 성과 평가 리스트 조회
    @PostMapping("/performance/list")
    public ResponseEntity<List<PerformanceShowDTO>> getAllPerformances() {
        List<PerformanceShowDTO> performances = performanceService.getAllPerformances();
        return new ResponseEntity<>(performances, HttpStatus.OK);
    }

}

