package com.megazone.ERPSystem_phase2_Backend.Integrated.controller;
import  com.megazone.ERPSystem_phase2_Backend.Integrated.model.dto.DashboardDataDTO;

import com.megazone.ERPSystem_phase2_Backend.Integrated.service.IntegratedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/integrated")
@RequiredArgsConstructor
public class IntegratedController {

    private final IntegratedService integratedService;

    @PostMapping("/dashboard")
    public ResponseEntity<Object> dashboard() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(integratedService.dashboard());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}