package com.megazone.ERPSystem_phase2_Backend.logistics.controller.sales_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.SearchDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.sale.SaleCreateDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.sale.SaleResponseDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.sale.SaleResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.sales_management.sale.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/logistics/sales")
public class SaleController {

    private final SaleService saleService;

    /**
     * 판매서 목록 조회
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<?> getSale(@RequestBody(required = false) SearchDTO dto) {
        System.out.println("dto = " + dto);
        List<SaleResponseDto> response = saleService.findAllSales(dto);

        if(response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("판매서가 한 건도 존재하지 않습니다.");
        }

        return ResponseEntity.ok(response);
    }
    /**
     * 판매서 상세 조회

     * @param id 판매서 ID
     * @return 판매서 상세 정보
     */
    @PostMapping("/{id}")
    public ResponseEntity<SaleResponseDetailDto> getSaleDetails(@PathVariable("id") Long id) {

        return saleService.findSaleDetailById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * 판매서 등록
     * @param createDto 판매서 생성 정보
     * @return 생성된 판매서 정보
     */
    @PostMapping("/create")
    public ResponseEntity<?> createSale(@RequestBody SaleCreateDto createDto) {
        SaleResponseDetailDto savedSale = saleService.createSale(createDto);
        return savedSale != null ?
                ResponseEntity.status(HttpStatus.CREATED).body(savedSale) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("판매서 생성에 실패했습니다.");
    }

    /**
     * 판매서 수정
     * @param id 판매서 ID
     * @param updateDto 수정할 판매서 정보
     * @return 수정된 판매서 정보
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<SaleResponseDetailDto> updateSale(@PathVariable("id") Long id, @RequestBody SaleCreateDto updateDto) {
        SaleResponseDetailDto updatedSale = saleService.updateSale(id, updateDto);
        return ResponseEntity.ok(updatedSale);
    }

    /**
     * 판매서 삭제
     * @param id 판매서 ID
     * @return 삭제 결과 메시지
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSale(@PathVariable("id") Long id) {
        String result = saleService.deleteSale(id);
        return ResponseEntity.ok(result);
    }
}
