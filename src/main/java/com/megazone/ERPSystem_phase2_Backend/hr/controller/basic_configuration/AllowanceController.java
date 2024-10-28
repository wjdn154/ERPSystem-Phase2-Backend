package com.megazone.ERPSystem_phase2_Backend.hr.controller.basic_configuration;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.AllowanceEntryDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.AllowanceShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_configuration.dto.AllowanceUpdateDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_configuration.AllowanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr")
public class AllowanceController {

    private final AllowanceService allowanceService;

    /**
     * 수당 과목 리스트 조회
     */
    @PostMapping("basicconfiguration/allowance/show")
    public ResponseEntity<Object> show(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(allowanceService.show());
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * 수당 과목 등록
     */
    @PostMapping("basicconfiguration/allowance/entry")
    public ResponseEntity<Object> entry(@RequestBody AllowanceEntryDTO dto){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(allowanceService.entry(dto));
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
