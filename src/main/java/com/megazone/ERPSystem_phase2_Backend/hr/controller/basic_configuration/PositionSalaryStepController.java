package com.megazone.ERPSystem_phase2_Backend.hr.controller.basic_configuration;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.PositionSalaryStep;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_configuration.PositionSalaryStepService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 직급별-호봉별-수당 금액 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr")
public class PositionSalaryStepController {
    private final PositionSalaryStepService positionSalaryStepService;

    @PostMapping("basicconfiguration/positionsalarystep/show")
    public ResponseEntity<Object> show() {
        try{
            Object result = positionSalaryStepService.show();
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
