//import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProductionRouting;
//import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto.ProductionRoutingDTO;
//import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto.RoutingStepDTO;
//import com.megazone.ERPSystem_phase2_Backend.production.repository.routing_management.ProductionRouting.ProductionRoutingRepository;
//import com.megazone.ERPSystem_phase2_Backend.production.service.routing_management.ProductionRouting.ProductionRoutingService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/production/productionRouting")
//@RequiredArgsConstructor
//public class ProductionRoutingController {
//
//    private final ProductionRoutingRepository productionRoutingRepository;
//    private final ProductionRoutingService productionRoutingService;
//
//    // GET ProductionRouting 전체조회
//    @GetMapping
//    public List<ProductionRoutingDTO> getAllProductionRoutings() {
//        return productionRoutingRepository.findAll().stream()
//                .map(productionRoutingService::convertToDTO)
//                .toList();
//    }
//
//    // POST 고유코드 중복 확인 후, 개별 등록
//    @PostMapping
//    public ProductionRouting createProductionRouting(@RequestBody ProductionRoutingDTO routingDTO, @RequestBody List<RoutingStepDTO> stepDTOs) {
//        return productionRoutingService.createProductionRoutingWithSteps(routingDTO, stepDTOs);
//    }
//
//    // PUT 각 ProductionRouting ID를 통해 개별 수정
//    @PutMapping("/{id}")
//    public ResponseEntity<String> updateProductionRouting(@PathVariable Long id, @RequestBody ProductionRoutingDTO productionRoutingDTO) {
//        boolean isUpdated = productionRoutingService.updateProductionRouting(id, productionRoutingDTO);
//        return isUpdated ? ResponseEntity.ok("생산 라우팅이 성공적으로 수정되었습니다.")
//                : ResponseEntity.badRequest().body("생산 라우팅 수정에 실패했습니다.");
//    }
//
//    // DELETE Routing 삭제 시, 사용 중인지 확인 후 진행 그게 아니면 경고 메시지 출력
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteProductionRouting(@PathVariable Long id) {
//        boolean isDeleted = productionRoutingService.deleteProductionRouting(id);
//        return isDeleted ? ResponseEntity.ok("생산 라우팅이 성공적으로 삭제되었습니다.")
//                : ResponseEntity.badRequest().body("해당 라우팅이 사용 중입니다. 삭제가 불가합니다.");
//    }
//}
