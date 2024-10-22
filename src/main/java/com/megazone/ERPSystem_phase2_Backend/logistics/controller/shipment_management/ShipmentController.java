package com.megazone.ERPSystem_phase2_Backend.logistics.controller.shipment_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.dto.ShipmentProductListResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.dto.ShipmentRequestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.dto.ShipmentResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipment_management.dto.ShipmentResponseListDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.shipment_management.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/api/logistics/shipment")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService shipmentService;

    @PostMapping("/")
    public ResponseEntity<List<ShipmentResponseListDTO>> getShipmentListByDateRange(@RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate) {

        List<ShipmentResponseListDTO> shipmentList = shipmentService.findShipmentListByDateRange(startDate, endDate);

        return (shipmentList != null && !shipmentList.isEmpty()) ?
                ResponseEntity.ok(shipmentList) :
                ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<ShipmentResponseDTO> getShipmentById(@PathVariable("id") Long id) {
        try {
            ShipmentResponseDTO shipmentResponseDTO = shipmentService.getShipmentById(id);
            return (shipmentResponseDTO != null) ?
                    ResponseEntity.status(HttpStatus.OK).body(shipmentResponseDTO) :
                    ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createShipment(@RequestBody ShipmentRequestDTO requestDTO) {
        if (requestDTO.getWarehouseId() == null || requestDTO.getEmployeeId() == null) {
            return ResponseEntity.badRequest().body("필수 값이 누락되었습니다.");
        }

        try {
            shipmentService.createShipment(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("출하 전표가 성공적으로 생성되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("서버 오류가 발생했습니다. 다시 시도해 주세요.");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateShipment(@PathVariable("id") Long shipmentId, @RequestBody ShipmentRequestDTO requestDTO) {
        try {
            shipmentService.updateShipment(shipmentId, requestDTO);
            return ResponseEntity.ok("출하 전표가 성공적으로 수정되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("유효하지 않은 입력 값입니다. 다시 확인해 주세요.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("서버 오류가 발생했습니다. 다시 시도해 주세요.");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteShipment(@PathVariable("id") Long shipmentId) {
        try {
            shipmentService.deleteShipment(shipmentId);
            return ResponseEntity.ok("출하 전표가 성공적으로 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("유효하지 않은 출하 전표 ID입니다. 삭제할 수 없습니다.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("서버 오류가 발생했습니다. 삭제에 실패했습니다.");
        }
    }

    /**
     * 출고 품목 조회
     * @param startDate 조회 시작일
     * @param endDate 조회 종료일
     * @return 출고 품목 리스트
     */
    @PostMapping("/items")
    public ResponseEntity<List<ShipmentProductListResponseDTO>> getShipmentItemsByDateRange(@RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate) {
        List<ShipmentProductListResponseDTO> shipmentItems = shipmentService.findShipmentItemsByDateRange(startDate, endDate);

        return (shipmentItems != null && !shipmentItems.isEmpty()) ?
                ResponseEntity.ok(shipmentItems) :
                ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}


