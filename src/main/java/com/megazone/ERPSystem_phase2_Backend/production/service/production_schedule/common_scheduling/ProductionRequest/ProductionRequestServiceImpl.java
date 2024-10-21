package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.common_scheduling.ProductionRequest;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.Client;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.client.ClientRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Department;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Department.DepartmentRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.Orders;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.ProductionRequestDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.ProductionRequest;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.enums.ProgressType;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.production_strategy.PlanOfMakeToOrder;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.common_scheduling.production_request.ProductionRequestsRepository;
import com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.planning.mps.MpsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductionRequestServiceImpl implements ProductionRequestService {

    private final ProductionRequestsRepository productionRequestsRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    private final MpsService mpsService;
//    private final OrdersRepository ordersRepository;

    @Override
    public ProductionRequestDTO saveManualProductionRequest(ProductionRequestDTO dto) {
        validateProductionRequestFields(dto);
        validateRelatedEntities(dto);

        if (isDuplicateRequest(dto)) {
            throw new IllegalArgumentException("동일한 생산요청이 이미 존재합니다.");
        }

        ProductionRequest entity = convertToEntity(dto);
        // 생성 시 자동으로 '작성' 상태 설정
        entity.setProgressType(ProgressType.CREATED);

        ProductionRequest savedEntity = productionRequestsRepository.save(entity);
        return convertToDTO(savedEntity);
    }

    // 중복방지 검증
    private boolean isDuplicateRequest(ProductionRequestDTO dto) {
        return productionRequestsRepository.existsByProductIdAndRequestDate(
                dto.getProductId(), dto.getRequestDate());
    }

    // 필수입력값
    private void validateProductionRequestFields(ProductionRequestDTO dto) {

        if (dto.getProductId() == null) {
            throw new IllegalArgumentException("제품을 선택해 주세요.");
        }
        if (dto.getRequestQuantity() == null || dto.getRequestQuantity().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("요청 수량은 0보다 커야 합니다.");
        }

    }

    // 연관엔티티 검증
    private void validateRelatedEntities(ProductionRequestDTO dto) {
        if (!clientRepository.existsById(dto.getClientId())) {
            throw new EntityNotFoundException("해당 클라이언트를 찾을 수 없습니다.");
        }
        if (!productRepository.existsById(dto.getProductId())) {
            throw new EntityNotFoundException("해당 제품을 찾을 수 없습니다.");
        }
//        if (!ordersRepository.existsById(dto.getSalesOrderId())) {
//            throw new EntityNotFoundException("해당 영업 주문을 찾을 수 없습니다.");
//        } todo
        if (!employeeRepository.existsById(dto.getRequesterId())) {
            throw new EntityNotFoundException("요청자를 찾을 수 없습니다.");
        }

    }



    @Override
    public ProductionRequestDTO getProductionRequestById(Long id) {
        ProductionRequest entity = productionRequestsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("생산 요청을 찾을 수 없습니다."));
        return convertToDTO(entity);
    }

    @Override
    public List<ProductionRequestDTO> getAllProductionRequests() {
        List<ProductionRequest> entities = productionRequestsRepository.findAll();
        return entities.stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public ProductionRequestDTO updateProductionRequest(Long id, ProductionRequestDTO dto) {
        ProductionRequest existingEntity = productionRequestsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("생산 요청을 찾을 수 없습니다."));
        // 엔티티 업데이트
        existingEntity.setName(dto.getName());
        existingEntity.setIsConfirmed(dto.getIsConfirmed());
        existingEntity.setRequestDate(dto.getRequestDate());
        existingEntity.setRequestQuantity(dto.getRequestQuantity());
        existingEntity.setConfirmedQuantity(dto.getConfirmedQuantity());

        // 연관된 엔티티는 ID로 조회해서 설정
        if (dto.getClientId() != null) {
            Client client = clientRepository.findById(dto.getClientId())
                    .orElseThrow(() -> new EntityNotFoundException("클라이언트를 찾을 수 없습니다."));
            existingEntity.setClient(client);
        }

        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new EntityNotFoundException("부서를 찾을 수 없습니다."));
            existingEntity.setProductionDepartment(department);
        }

        if (dto.getProductId() != null) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("제품을 찾을 수 없습니다."));
            existingEntity.setProduct(product);
        }

//        if (dto.getSalesOrderId() != null) {
//            Orders salesOrder = ordersRepository.findById(dto.getSalesOrderId())
//                    .orElseThrow(() -> new EntityNotFoundException("영업 주문을 찾을 수 없습니다."));
//            existingEntity.setSalesOrder(salesOrder);
//        }

        if (dto.getRequesterId() != null) {
            Employee requester = employeeRepository.findById(dto.getRequesterId())
                    .orElseThrow(() -> new EntityNotFoundException("요청자를 찾을 수 없습니다."));
            existingEntity.setRequester(requester);
        }

        ProductionRequest updatedEntity = productionRequestsRepository.save(existingEntity);
        return convertToDTO(updatedEntity);
    }

    @Override
    public void deleteProductionRequest(Long id) {
        ProductionRequest entity = productionRequestsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("생산 요청을 찾을 수 없습니다."));

        // 확정된 생산 요청이면 삭제할 수 없음
        if (entity.getIsConfirmed()) {
            throw new IllegalStateException("확정된 생산 요청은 삭제할 수 없습니다.");
        }

        productionRequestsRepository.delete(entity);
    }

    /**
     * 자동 생성된 생산 요청 저장
     */
    public ProductionRequestDTO saveAutoProductionRequest(ProductionRequestDTO dto) {
        ProductionRequest entity = convertToEntity(dto);

        // 자동 생성 요청의 경우, 추가적인 처리나 검증이 필요할 수 있음
        // 예: 시스템에 의해 자동으로 생성된 요청임을 표시
        entity.setIsConfirmed(false); // 기본적으로 승인되지 않은 상태로 설정
        ProductionRequest savedEntity = productionRequestsRepository.save(entity);
        return convertToDTO(savedEntity);
    }

    /**
     * 생산 요청 승인 및 MPS 자동 생성
     */
    public ProductionRequestDTO confirmProductionRequest(Long id, BigDecimal confirmedQuantity) {
        ProductionRequest request = productionRequestsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("생산 요청을 찾을 수 없습니다."));

        // 1. 이미 확정된 경우 처리
        if (request.getIsConfirmed()) {
            throw new IllegalStateException("이미 확정된 생산 요청입니다.");
        }

        // 2. 납기일과 완료 요청일 검증
        if (request.getDeadlineOfCompletion() != null &&
                request.getDueDateToProvide() != null &&
                request.getDueDateToProvide().isBefore(request.getDeadlineOfCompletion())) {
            throw new IllegalArgumentException("납기일이 완료 요청일자보다 빠를 수 없습니다.");
        }

        if (confirmedQuantity.compareTo(request.getRequestQuantity()) > 0) {
            throw new IllegalArgumentException("확정 수량이 요청 수량을 초과할 수 없습니다.");
        }

        // 확정 후 상태 변경 및 MPS 생성
        request.setConfirmedQuantity(confirmedQuantity);
        request.setIsConfirmed(true);
        request.setProgressType(ProgressType.NOT_STARTED);

        // MPS 자동 생성
//        mpsService.createMps(request); TODO

        ProductionRequest updatedRequest = productionRequestsRepository.save(request);
        return convertToDTO(updatedRequest);
    }

    /**
     * MPS 진행에 따른 상태 변경
     */
    public void updateProgressToInProgress(Long productionRequestId) {
        ProductionRequest request = productionRequestsRepository.findById(productionRequestId)
                .orElseThrow(() -> new EntityNotFoundException("생산 요청을 찾을 수 없습니다."));

        request.setProgressType(ProgressType.IN_PROGRESS);
        productionRequestsRepository.save(request);
    }

    /**
     * 생산 의뢰 상태 업데이트 (ProgressType에 따라)
     */
    public void updateProgress(Long requestId, ProgressType progressType) {
        ProductionRequest request = productionRequestsRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("생산 요청을 찾을 수 없습니다."));

        request.setProgressType(progressType);
        productionRequestsRepository.save(request);
    }

    // DTO와 엔티티 변환 메서드
    private ProductionRequest convertToEntity(ProductionRequestDTO dto) {

        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("클라이언트를 찾을 수 없습니다."));
        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("부서를 찾을 수 없습니다."));
        Employee requester = employeeRepository.findById(dto.getRequesterId())
                .orElseThrow(() -> new EntityNotFoundException("요청자를 찾을 수 없습니다."));

        return ProductionRequest.builder()
                .id(dto.getId())
                .name(dto.getName())
                .isConfirmed(dto.getIsConfirmed())
                .requestDate(dto.getRequestDate())
                .deadlineOfCompletion(dto.getDeadlineOfCompletion())
                .dueDateToProvide(dto.getDueDateToProvide())
                .requestQuantity(dto.getRequestQuantity())
                .confirmedQuantity(dto.getConfirmedQuantity())
                .product(Product.builder().id(dto.getProductId()).build())
                .client(client)
                .productionDepartment(department)
                .salesOrder(Orders.builder().id(dto.getSalesOrderId()).build())
                .requester(requester)
                .remarks(dto.getRemarks())
                .build();
    }

    private ProductionRequestDTO convertToDTO(ProductionRequest entity) {
        return ProductionRequestDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .isConfirmed(entity.getIsConfirmed())
                .requestDate(entity.getRequestDate())
                .deadlineOfCompletion(entity.getDeadlineOfCompletion())
                .dueDateToProvide(entity.getDueDateToProvide())
                .requestQuantity(entity.getRequestQuantity())
                .confirmedQuantity(entity.getConfirmedQuantity())
                .remarks(entity.getRemarks())
                .clientId(entity.getClient().getId() != null ? entity.getClient().getId() : null)
                .departmentId(entity.getProductionDepartment().getId() != null ? entity.getProductionDepartment().getId() : null)
                .productId(entity.getProduct().getId() != null ? entity.getProduct().getId() : null)
                .salesOrderId(entity.getSalesOrder().getId() != null ? entity.getSalesOrder().getId() : null)
                .requesterId(entity.getRequester().getId() != null ? entity.getRequester().getId() : null)
                .build();
    }
}
