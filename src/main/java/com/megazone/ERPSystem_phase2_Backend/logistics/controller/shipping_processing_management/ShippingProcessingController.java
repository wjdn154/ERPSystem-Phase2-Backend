package com.megazone.ERPSystem_phase2_Backend.logistics.controller.shipping_processing_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipping_processing_management.dto.ShippingProcessingRequestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipping_processing_management.dto.ShippingProcessingResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.shipping_processing_management.ShippingProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/logistics/shipping-process")
public class ShippingProcessingController {

    private final ShippingProcessingService shippingProcessingService;

    @PostMapping("/list")
    public ResponseEntity<List<ShippingProcessingResponseDTO>> getShippingProcessingList(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        List<ShippingProcessingResponseDTO> response = shippingProcessingService.getShippingProcessingList(startDate, endDate);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerShippingProcessing(@RequestBody ShippingProcessingRequestDTO requestDTO) {
        try {
            shippingProcessingService.registerShippingProcessing(requestDTO);
            return ResponseEntity.ok("출고처리등록이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/process/{id}")
    public ResponseEntity<String> processShipping(@PathVariable Long id) {
        try {
            shippingProcessingService.processShipping(id);
            return ResponseEntity.ok("출고 처리가 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("출고 처리 중 오류가 발생했습니다.");
        }
    }


}
