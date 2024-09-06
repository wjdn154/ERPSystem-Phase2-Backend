package com.megazone.ERPSystem_phase2_Backend.production.controller.production_schedule;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.WorkOrderDTO;
import com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.work_order.WorkOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/production/workOrders")
@RequiredArgsConstructor
public class WorkOrderController {

    private final WorkOrderService workOrderService;
    /**
     * 작업 지시 생성
     *
     * @param workOrderDTO 작업 지시 정보
     * @return 생성된 작업 지시 정보
     */

    @PostMapping
    public ResponseEntity<WorkOrderDTO> createWorkOrder(@RequestBody WorkOrderDTO workOrderDTO) {
        WorkOrderDTO createdWorkOrder = workOrderService.createWorkOrder(workOrderDTO);
        return ResponseEntity.ok(createdWorkOrder);
    }

    /**
     * 작업 지시에 작업자 배정
     *
     * @param workOrderDTO 작업 지시 정보
     * @return 성공 시 200 OK
     */
    @PostMapping("/{workOrderId}/assignWorkers")
    public ResponseEntity<String> assignWorkersToWorkcenter(@PathVariable Long workOrderId, @RequestBody WorkOrderDTO workOrderDTO) {
        try {
            workOrderService.assignWorkersToWorkcenter(workOrderDTO);
            return ResponseEntity.ok("작업자 배정이 성공적으로 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            // 중복 배정 검증 실패 시 에러 메시지 반환
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 모든 작업 지시 조회
     *
     * @return 모든 작업 지시 정보 리스트
     */
    @GetMapping
    public ResponseEntity<List<WorkOrderDTO>> getAllWorkOrders() {
        List<WorkOrderDTO> workOrders = workOrderService.getAllWorkOrders();
        return ResponseEntity.ok(workOrders);
    }

    /**
     * 작업 지시 ID로 단일 작업 지시 조회
     *
     * @param workOrderId 작업 지시 ID
     * @return 작업 지시 정보
     */
    @GetMapping("/{workOrderId}")
    public ResponseEntity<WorkOrderDTO> getWorkOrderById(@PathVariable Long workOrderId) {
        WorkOrderDTO workOrder = workOrderService.getWorkOrderById(workOrderId);
        return ResponseEntity.ok(workOrder);
    }

    /**
     * 작업 지시 수정
     *
     * @param workOrderId  수정할 작업 지시 ID
     * @param workOrderDTO 수정할 작업 지시 정보
     * @return 수정된 작업 지시 정보
     */
    @PutMapping("/{workOrderId}")
    public ResponseEntity<WorkOrderDTO> updateWorkOrder(@PathVariable Long workOrderId, @RequestBody WorkOrderDTO workOrderDTO) {
        WorkOrderDTO updatedWorkOrder = workOrderService.updateWorkOrder(workOrderId, workOrderDTO);
        return ResponseEntity.ok(updatedWorkOrder);
    }

    /**
     * 작업 지시 삭제
     *
     * @param workOrderId 삭제할 작업 지시 ID
     * @return 성공 시 200 OK
     */
    @DeleteMapping("/{workOrderId}")
    public ResponseEntity<String> deleteWorkOrder(@PathVariable Long workOrderId) {
        workOrderService.deleteWorkOrder(workOrderId);
        return ResponseEntity.ok("작업 지시가 성공적으로 삭제되었습니다.");
    }
}

