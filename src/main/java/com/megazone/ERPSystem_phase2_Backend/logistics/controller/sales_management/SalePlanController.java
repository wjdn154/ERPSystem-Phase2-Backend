package com.megazone.ERPSystem_phase2_Backend.logistics.controller.sales_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.SearchDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.sale_plan.SalePlanResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.sales_management.salel_plan.SalePlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/logistics/sale-plans")
public class SalePlanController {

    private final SalePlanService salePlanService;

    /**
     * 판매 계획 목록 조회
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<?> getAllSalePlans(@RequestBody(required = false) SearchDTO dto) {
        System.out.println("dto = " + dto);
        List<SalePlanResponseDto> response = salePlanService.findAllSalePlans(dto);

        if(response.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("판매 계획이 한 건도 존재하지 않습니다.");
        }

        return ResponseEntity.ok(response);
    }
}