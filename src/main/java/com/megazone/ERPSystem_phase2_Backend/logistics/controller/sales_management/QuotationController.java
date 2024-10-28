package com.megazone.ERPSystem_phase2_Backend.logistics.controller.sales_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseCreateDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.SearchDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.quotation.QuotationCreateDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.quotation.QuotationResponseDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.quotation.QuotationResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.sales_management.quotation.QuotationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/logistics/quotations")
public class QuotationController {

    private final QuotationService quotationService;

    /**
     * 견적서 목록 조회
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<?> getQuotation(@RequestBody(required = false) SearchDTO dto) {
        System.out.println("dto = " + dto);
        List<QuotationResponseDto> response = quotationService.findAllQuotations(dto);

        if(response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("견적서가 한 건도 존재하지 않습니다.");
        }

        return ResponseEntity.ok(response);
    }
    /**
     * 견적서 상세 조회

     * @param id 견적서 ID
     * @return 견적서 상세 정보
     */
    @PostMapping("/{id}")
    public ResponseEntity<QuotationResponseDetailDto> getQuotationDetails(@PathVariable("id") Long id) {

        return quotationService.findQuotationDetailById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * 견적서 등록
     * @param createDto 견적서 생성 정보
     * @return 생성된 견적서 정보
     */
    @PostMapping("/create")
    public ResponseEntity<?> createQuotation(@RequestBody QuotationCreateDto createDto) {
        QuotationResponseDetailDto savedQuotation = quotationService.createQuotation(createDto);
        return savedQuotation != null ?
                ResponseEntity.status(HttpStatus.CREATED).body(savedQuotation) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("견적서 생성에 실패했습니다.");
    }

    /**
     * 견적서 수정
     * @param id 견적서 ID
     * @param updateDto 수정할 견적서 정보
     * @return 수정된 견적서 정보
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<QuotationResponseDetailDto> updateQuotation(@PathVariable("id") Long id, @RequestBody QuotationCreateDto updateDto) {
        QuotationResponseDetailDto updatedQuotation = quotationService.updateQuotation(id, updateDto);
        return ResponseEntity.ok(updatedQuotation);
    }

    /**
     * 견적서 삭제
     * @param id 견적서 ID
     * @return 삭제 결과 메시지
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuotation(@PathVariable("id") Long id) {
        String result = quotationService.deleteQuotation(id);
        return ResponseEntity.ok(result);
    }
}
