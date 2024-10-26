package com.megazone.ERPSystem_phase2_Backend.hr.controller.basic_configuration;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.SalaryStep;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.SalaryStepEntryDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.SalaryStepShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_configuration.SalaryStepService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr")
public class SalaryStepController {
    private final SalaryStepService salaryStepService;

    @PostMapping("basic_configuration/show")
    public ResponseEntity<Object> show() {
        try{
            List<SalaryStepShowDTO> result = salaryStepService.show();
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("basic_configuration/entry")
    public ResponseEntity<Object> entry(SalaryStepEntryDTO dto) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(salaryStepService.entry(dto));
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
