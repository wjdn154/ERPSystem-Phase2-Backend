package com.megazone.ERPSystem_phase2_Backend.production.controller.production_schedule.common_scheduling;

import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.company.CompanyRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.ProductionOrderDTO;
import com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.common_scheduling.ProductionOrder.ProductionOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/production/productionOrders")
@RequiredArgsConstructor
public class ProductionOrderController {

    private final ProductionOrderService productionOrderService;
    private final CompanyRepository companyRepository;
    private final UsersRepository usersRepository;

    // JWT 인증에서 사용자 정보를 가져오는 메서드
    private Long getCurrentCompanyId() {
        Users user = usersRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        return user.getCompany().getId();
    }

    /**
     * 작업 지시 생성
     *
     * @param productionOrderDTO 작업 지시 정보
     * @return 생성된 작업 지시 정보
     */

    @PostMapping
    public ResponseEntity<ProductionOrderDTO> createProductionOrder(@RequestBody ProductionOrderDTO productionOrderDTO) {
        ProductionOrderDTO createdProductionOrder = productionOrderService.createProductionOrder(productionOrderDTO);
        return ResponseEntity.ok(createdProductionOrder);
    }

    /**
     * 작업 지시에 작업자 배정
     *
     * @param productionOrderDTO 작업 지시 정보
     * @return 성공 시 200 OK
     */
    @PostMapping("/{productionOrderId}/assignWorkers")
    public ResponseEntity<String> assignWorkersToWorkcenter(@PathVariable Long productionOrderId, @RequestBody ProductionOrderDTO productionOrderDTO) {
        try {
            productionOrderService.assignWorkersToWorkcenter(productionOrderDTO);
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
    @PostMapping
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
    public ResponseEntity<ProductionOrderDTO> getProductionOrderById(@PathVariable Long productionOrderId) {
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
    @PutMapping("/{productionOrderId}")
    public ResponseEntity<ProductionOrderDTO> updateProductionOrder(@PathVariable Long productionOrderId, @RequestBody ProductionOrderDTO productionOrderDTO) {
        ProductionOrderDTO updatedProductionOrder = productionOrderService.updateProductionOrder(productionOrderId, productionOrderDTO);
        return ResponseEntity.ok(updatedProductionOrder);
    }

    /**
     * 작업 지시 삭제
     *
     * @param productionOrderId 삭제할 작업 지시 ID
     * @return 성공 시 200 OK
     */
    @DeleteMapping("/{productionOrderId}")
    public ResponseEntity<String> deleteProductionOrder(@PathVariable Long productionOrderId) {
        productionOrderService.deleteProductionOrder(productionOrderId);
        return ResponseEntity.ok("작업 지시가 성공적으로 삭제되었습니다.");
    }
}

