package com.megazone.ERPSystem_phase2_Backend.logistics.controller.purchase_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseCreateDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseResponseDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.SearchDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.purchase_management.purchase.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/logistics/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    /**
     * 구매서 목록 조회
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<?> getPurchase(@RequestBody(required = false) SearchDTO dto) {
        System.out.println("dto = " + dto);
        List<PurchaseResponseDto> response = purchaseService.findAllPurchases(dto);

        if(response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("구매서가 한 건도 존재하지 않습니다.");
        }

        return ResponseEntity.ok(response);
    }

    /**
     * 구매서 상세 조회
     * @param id 구매서 ID
     * @return 구매서 상세 정보
     */
    @PostMapping("/{id}")
    public ResponseEntity<PurchaseResponseDetailDto> getPurchaseDetails(@PathVariable("id") Long id) {

        return purchaseService.findPurchaseDetailById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * 구매서 등록
     * @param createDto 구매서 생성 정보
     * @return 생성된 구매서 정보
     */
    @PostMapping("/create")
    public ResponseEntity<?> createPurchase(@RequestBody PurchaseCreateDto createDto) {
        PurchaseResponseDetailDto savedPurchase = purchaseService.createPurchase(createDto);
        return savedPurchase != null ?
                ResponseEntity.status(HttpStatus.CREATED).body(savedPurchase) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("구매서 생성에 실패했습니다.");
    }

    /**
     * 구매서 수정
     * @param id 구매서 ID
     * @param updateDto 수정할 구매서 정보
     * @return 수정된 구매서 정보
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<PurchaseResponseDetailDto> updatePurchase(@PathVariable("id") Long id, @RequestBody PurchaseCreateDto updateDto) {
        PurchaseResponseDetailDto updatedPurchase = purchaseService.updatePurchase(id, updateDto);
        return ResponseEntity.ok(updatedPurchase);
    }

    /**
     * 구매서 삭제
     * @param id 구매서 ID
     * @return 삭제 결과 메시지
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePurchase(@PathVariable("id") Long id) {
        String result = purchaseService.deletePurchase(id);
        return ResponseEntity.ok(result);
    }

}
