package com.megazone.ERPSystem_phase2_Backend.hr.controller.basic_information_management.Salary;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.SalaryEntryDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.SalaryShowDto;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.salary.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr")
public class SalaryController {

    private final SalaryService salaryService;

    @PostMapping("salary/entry")
    public ResponseEntity<Object> entry(@RequestBody SalaryEntryDTO dto) {
        try {
            salaryService.saveSalary(dto);
            return ResponseEntity.status(HttpStatus.OK).body("급여정보 등록이 완료되었습니다.");
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("salary/show")
    public ResponseEntity<Object> show(@RequestBody Map<String, Long> requestId) {
        Long employeeId = requestId.get("employeeId");
        try {
            SalaryShowDto result = salaryService.show(employeeId);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
