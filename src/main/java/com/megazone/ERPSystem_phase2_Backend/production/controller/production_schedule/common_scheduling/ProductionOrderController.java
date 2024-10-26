package com.megazone.ERPSystem_phase2_Backend.production.controller.production_schedule.common_scheduling;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.UnresolvedSaleAndPurchaseVoucher;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.ProductionOrder;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.ProductionOrderDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.dto.WorkPerformanceDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.work_report.dto.WorkPerformanceUpdateDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.common_scheduling.production_order.ProductionOrderRepository;
import com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.common_scheduling.ProductionOrder.ProductionOrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.tools.ant.taskdefs.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/production/productionOrder")
@RequiredArgsConstructor
public class ProductionOrderController {

    private final ProductionOrderService productionOrderService;
    private final ProductionOrderRepository productionOrderRepository;

    /**
     * 작업 지시 저장
     *
     * @param productionOrderDTO 작업 지시 정보
     * @return 생성된 작업 지시 정보
     */

    @PostMapping("/save")
    public ResponseEntity<ProductionOrderDTO> saveProductionOrder(@RequestBody ProductionOrderDTO productionOrderDTO) {
        ProductionOrderDTO savedProductionOrder = productionOrderService.saveProductionOrder(productionOrderDTO);
        return ResponseEntity.ok(savedProductionOrder);
    }

    /**
     * 작업 지시에 작업자 배정
     *
     * @param productionOrderId 작업 지시 ID
     * @param productionOrderDTO 작업 지시 정보 (작업자 배정 정보 포함)
     * @return 성공 시 200 OK
     */
    @PostMapping("/{productionOrderId}/assignWorkers")
    public ResponseEntity<String> assignWorkersToWorkcenter(@PathVariable("productionOrderId") Long productionOrderId, @RequestBody ProductionOrderDTO productionOrderDTO) {
        try {
            ProductionOrder productionOrder = productionOrderRepository.findById(productionOrderId)
                    .orElseThrow(() -> new EntityNotFoundException("작업지시를 찾을 수 없습니다."));
            productionOrderService.assignWorkersToWorkcenter(productionOrderDTO, productionOrder);

            return ResponseEntity.ok("작업자 배정이 성공적으로 완료되었습니다.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("작업 지시를 찾을 수 없습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // 중복 배정 검증 실패 시 에러 메시지 반환
//        } catch (Exception e) {
//            // 500 Internal Server Error에 대한 처리
//            log.error("서버 오류 발생: ", e);  // 서버 로그에 구체적인 오류 정보 기록
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버에 문제가 발생했습니다. 잠시 후 다시 시도해주세요.");
        }
    }

    /**
     * 작업 지시 마감 여부 업데이트
     * - WorkPerformance 상태 변경에 따른 작업 지시 마감 처리
     */
    @PostMapping("/closure")
    public ResponseEntity<Object> updateOrderClosure(@RequestBody WorkPerformanceUpdateDTO dto) {
        System.out.println("dto = " + dto.toString());
        System.out.println(LocalDateTime.now());
        try{
            WorkPerformanceDTO workPerformanceDTO = productionOrderService.updateOrderClosure(dto);
            return ResponseEntity.status(HttpStatus.OK).body(workPerformanceDTO);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("마감처리 실패, 이유 : " + e.getMessage());
        }

    }


    /**
     * 모든 작업 지시 조회
     *
     * @return 모든 작업 지시 정보 리스트
     */
    @PostMapping("/all")
    public ResponseEntity<List<ProductionOrderDTO>> getAllProductionOrders() {
        List<ProductionOrderDTO> productionOrders = productionOrderService.getAllProductionOrders();
        return ResponseEntity.ok(productionOrders);
    }

    /**
     * 작업 지시 ID로 단일 작업 지시 조회
     *
     * @param productionOrderId 작업 지시 ID
     * @return 작업 지시 정보
     */
    @PostMapping("/{productionOrderId}")
    public ResponseEntity<ProductionOrderDTO> getProductionOrderById(@PathVariable("productionOrderId") Long productionOrderId) {
        ProductionOrderDTO productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        return ResponseEntity.ok(productionOrder);
    }

    /**
     * 작업 지시 수정
     *
     * @param productionOrderId  수정할 작업 지시 ID
     * @param productionOrderDTO 수정할 작업 지시 정보
     * @return 수정된 작업 지시 정보
     */
    @PostMapping("/update/{productionOrderId}")
    public ResponseEntity<ProductionOrderDTO> updateProductionOrder(@PathVariable("productionOrderId") Long productionOrderId, @RequestBody ProductionOrderDTO productionOrderDTO) {
        ProductionOrderDTO updatedProductionOrder = productionOrderService.updateProductionOrder(productionOrderId, productionOrderDTO);
        return ResponseEntity.ok(updatedProductionOrder);
    }

    /**
     * 작업 지시 삭제
     *
     * @param productionOrderId 삭제할 작업 지시 ID
     * @return 성공 시 200 OK
     */
    @PostMapping("/delete/{productionOrderId}")
    public ResponseEntity<String> deleteProductionOrder(@PathVariable("productionOrderId") Long productionOrderId) {
        productionOrderService.deleteProductionOrder(productionOrderId);
        return ResponseEntity.ok("작업 지시가 성공적으로 삭제되었습니다.");
    }

    /**
     * 작업 지시 확정
     */
    @PostMapping("/confirm/{id}")
    public ResponseEntity<Object> confirmProductionOrder(@PathVariable("id") Long id) {
        try {
            System.out.println("id = " + id);
            boolean isConfirmed = productionOrderService.isProductionOrderConfirmed(id);
            return isConfirmed ? ResponseEntity.status(HttpStatus.OK).body("작업 지시가 성공적으로 확정되었습니다.") :
                    ResponseEntity.status(HttpStatus.NO_CONTENT).body("작업 지시 확정에 실패했습니다.");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("작업 지시 확정에 실패했습니다.");
        }
    }

}

