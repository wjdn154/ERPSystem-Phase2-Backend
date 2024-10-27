package com.megazone.ERPSystem_phase2_Backend.logistics.controller.receiving_processing_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.receiving_processing_management.dto.ReceivingOrderDetailResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.receiving_processing_management.dto.ReceivingOrderProcessRequestListDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.receiving_processing_management.dto.ReceivingScheduleResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.purchase_management.receiving_order.ReceivingOrderDetailService;
import com.megazone.ERPSystem_phase2_Backend.logistics.service.receiving_schedule_management.ReceivingScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/logistics/receivingSchedules")
public class ReceivingScheduleController {

    private final ReceivingScheduleService receivingScheduleService;
    private final ReceivingOrderDetailService receivingOrderDetailService;

    /**
     * 입고지시상세품목 목록 조회
     * @param startDate 조회 시작일
     * @param endDate 조회 종료일
     * @return HttpStatus.OK or HttpStatus.NO_CONTENT or HttpStatus.INTERNAL_SERVER_ERROR or HttpStatus.BAD_REQUEST
     */
    @PostMapping("/waitingReceipt")
    public ResponseEntity<List<ReceivingOrderDetailResponseDTO>> getReceivingOrderDetailsByDateRange(@RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate) {
        List<ReceivingOrderDetailResponseDTO> details = receivingOrderDetailService.getReceivingOrderDetailsWithWaitingQuantityByDateRange(startDate, endDate);
        return ResponseEntity.ok(details);
    }



    @PostMapping("/waiting")
    public ResponseEntity<List<ReceivingScheduleResponseDTO>> getReceivingSchedulesByDateRange(@RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate) {
        List<ReceivingScheduleResponseDTO> schedules = receivingScheduleService.getReceivingSchedulesByDateRange(startDate, endDate);
        return ResponseEntity.ok(schedules);
    }

    @PostMapping("/process")
    public ResponseEntity<String> processReceivingSchedules(@RequestBody ReceivingOrderProcessRequestListDTO requestListDTO) {
        receivingScheduleService.registerReceivingSchedules(requestListDTO);
        return ResponseEntity.ok("Receiving schedules processed successfully.");
    }

    @PostMapping("/process/{id}")
    public ResponseEntity<String> processReceivingSchedule(@PathVariable("id") Long id) {
        receivingScheduleService.processReceivingSchedule(id);
        return ResponseEntity.status(HttpStatus.CREATED).body("Inventory registration successful.");
    }
}

