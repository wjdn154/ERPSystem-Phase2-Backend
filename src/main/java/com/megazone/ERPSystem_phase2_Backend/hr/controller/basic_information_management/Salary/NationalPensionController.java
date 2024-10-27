package com.megazone.ERPSystem_phase2_Backend.hr.controller.basic_information_management.Salary;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.InsurancePensionCalculatorDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.NationalPensionShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.salary.NationalPensionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr")
public class NationalPensionController {

    private final NationalPensionService nationalPensionService;

    @PostMapping("national_pension/calculator")
    public ResponseEntity<Object> calculator(@RequestBody InsurancePensionCalculatorDTO dto) {
        try {
            BigDecimal resultAmount = nationalPensionService.calculator(dto);
            return ResponseEntity.status(HttpStatus.OK).body(resultAmount);
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("national_pension/show")
    public ResponseEntity<Object> showAll() {
        try {
            List<NationalPensionShowDTO> result = nationalPensionService.showAll();
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
