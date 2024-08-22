package com.megazone.ERPSystem_phase2_Backend.production.controller.routing_management;

import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProductionRouting;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto.ProductionRoutingDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto.RoutingStepDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.routing_management.ProductionRouting.ProductionRoutingRepository;
import com.megazone.ERPSystem_phase2_Backend.production.service.routing_management.ProductionRouting.ProductionRoutingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/production/productionRouting")
@RequiredArgsConstructor
public class ProductionRoutingController {

    private final ProductionRoutingRepository productionRoutingRepository;
    private final ProductionRoutingService productionRoutingService;

    // GET ProductionRouting 전체조회
    @GetMapping
    public List<ProductionRoutingDTO> getAllProductionRoutings() {
        return productionRoutingRepository.findAll().stream()
                .map(productionRoutingService::convertToDTO) // 서비스 계층의 convertToDTO 메서드 호출
                .collect(Collectors.toList());
    }

    // POST 고유코드 중복 확인 후, 개별 등록
    @PostMapping
    public ResponseEntity<ProductionRouting> createProductionRouting(@RequestBody ProductionRoutingDTO routingDTO, @RequestBody List<RoutingStepDTO> stepDTOs) {
        // 서비스 계층에서 반환된 값을 Optional로 감쌉니다.
        ProductionRouting createdRouting = productionRoutingService.createProductionRoutingWithSteps(routingDTO, stepDTOs);

        // createdRouting이 null이 아닌 경우 성공 응답, null인 경우 실패 응답 반환
        return Optional.ofNullable(createdRouting)
                .map(routing -> ResponseEntity.ok().body(routing)) // 성공 시 200 OK 응답
                .orElse(ResponseEntity.badRequest().body(null)); // 실패 시 400 Bad Request 응답
    }

    // PUT 각 ProductionRouting ID를 통해 개별 수정
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProductionRouting(@PathVariable Long id, @RequestBody ProductionRoutingDTO productionRoutingDTO) {
        Optional<ProductionRouting> updatedRouting = productionRoutingService.updateProductionRouting(id, productionRoutingDTO);
        return updatedRouting.isPresent() ? ResponseEntity.ok("생산 라우팅이 성공적으로 수정되었습니다.")
                : ResponseEntity.badRequest().body("생산 라우팅을 수정할 수 없습니다.");
    }

    // DELETE Routing 삭제 시, 사용 중인지 확인 후 진행 그게 아니면 경고 메시지 출력
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductionRouting(@PathVariable Long id) {
        Optional<ProductionRouting> deletedRouting = productionRoutingService.deleteProductionRouting(id);
        return deletedRouting.isPresent() ? ResponseEntity.ok("생산 라우팅이 성공적으로 삭제되었습니다.")
                : ResponseEntity.badRequest().body("해당 라우팅이 사용 중입니다. 삭제가 불가합니다.");
    }
}
