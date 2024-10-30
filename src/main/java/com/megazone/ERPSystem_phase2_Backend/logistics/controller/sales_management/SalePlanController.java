package com.megazone.ERPSystem_phase2_Backend.logistics.controller.sales_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.SearchDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.sale_plan.SalePlanCreateDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.sale_plan.SalePlanResponseDetailDto;
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
        List<SalePlanResponseDto> response = salePlanService.findAllSalePlans(dto);

        if(response.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("판매 계획이 한 건도 존재하지 않습니다.");
        }

        return ResponseEntity.ok(response);
    }

    /**
     * 판매계획 상세 조회

     * @param id 판매계획 ID
     * @return 판매계획 상세 정보
     */
    @PostMapping("/{id}")
    public ResponseEntity<SalePlanResponseDetailDto> getSalePlanDetails(@PathVariable("id") Long id) {

        return salePlanService.findSalePlanDetailById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * 판매계획 등록
     * @param createDto 판매계획 생성 정보
     * @return 생성된 판매계획 정보
     */
    @PostMapping("/create")
    public ResponseEntity<?> createSalePlan(@RequestBody SalePlanCreateDto createDto) {
        SalePlanResponseDetailDto savedSalePlan = salePlanService.createSalePlan(createDto);
        return savedSalePlan != null ?
                ResponseEntity.status(HttpStatus.CREATED).body(savedSalePlan) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("판매계획 생성에 실패했습니다.");
    }

    /**
     * 판매계획 수정
     * @param id 판매계획 ID
     * @param updateDto 수정할 판매계획 정보
     * @return 수정된 판매계획 정보
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<SalePlanResponseDetailDto> updateSalePlan(@PathVariable("id") Long id, @RequestBody SalePlanCreateDto updateDto) {
        SalePlanResponseDetailDto updatedSalePlan = salePlanService.updateSalePlan(id, updateDto);
        return ResponseEntity.ok(updatedSalePlan);
    }

    /**
     * 판매계획 삭제
     * @param id 판매계획 ID
     * @return 삭제 결과 메시지
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSalePlan(@PathVariable("id") Long id) {
        String result = salePlanService.deleteSalePlan(id);
        return ResponseEntity.ok(result);
    }
}
