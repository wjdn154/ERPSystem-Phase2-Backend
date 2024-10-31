package com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.common_scheduling.ProductionRequest;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.Client;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.client.ClientRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Department;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Department.DepartmentRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.ProductionRequestListDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.ProductionRequest;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto.ProductionRequestDetailDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.enums.ProgressType;
import com.megazone.ERPSystem_phase2_Backend.production.repository.production_schedule.common_scheduling.production_request.ProductionRequestsRepository;
import com.megazone.ERPSystem_phase2_Backend.production.service.production_schedule.planning.mps.MpsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    @Override
    public ProductionRequestDetailDTO saveManualProductionRequest(ProductionRequestDetailDTO dto) {
        validateProductionRequestFields(dto);
        validateRelatedEntities(dto);

        if (isDuplicateRequest(dto)) {
            throw new IllegalArgumentException("동일한 생산요청이 이미 존재합니다.");
        }

        ProductionRequest request = dto.toEntity();
        // 기본 상태 설정
        if (dto.getProgressType() == null) {
            request.setProgressType(ProgressType.CREATED);
        }

        ProductionRequest savedRequest = productionRequestsRepository.save(request);
        return ProductionRequestDetailDTO.fromEntity(savedRequest);
    }

    // 중복 방지 검증
    private boolean isDuplicateRequest(ProductionRequestDetailDTO dto) {
        List<ProductionRequest> existingRequests =
                productionRequestsRepository.findByProductIdAndClientId(dto.getProductId(), dto.getClientId());

        return existingRequests.stream().anyMatch(existingRequest ->
                existingRequest.getRequestDate().isEqual(LocalDate.parse(dto.getRequestDate())) &&
                        existingRequest.getDeadlineOfCompletion().isEqual(LocalDate.parse(dto.getDeadlineOfCompletion())) &&
                        existingRequest.getDueDateToProvide().isEqual(LocalDate.parse(dto.getDueDateToProvide())) &&
                        existingRequest.getRequestQuantity().equals(dto.getRequestQuantity())
        );
    }

    // 필수 입력값 검증
    private void validateProductionRequestFields(ProductionRequestDetailDTO dto) {
        if (dto.getProductId() == null) {
            throw new IllegalArgumentException("제품을 선택해 주세요.");
        }
        if (dto.getRequestQuantity() == null || dto.getRequestQuantity() <= 0) {
            throw new IllegalArgumentException("요청 수량은 0보다 커야 합니다.");
        }
    }

    // 연관 엔티티 검증
    private void validateRelatedEntities(ProductionRequestDetailDTO dto) {
        if (!clientRepository.existsById(dto.getClientId())) {
            throw new EntityNotFoundException("해당 클라이언트를 찾을 수 없습니다.");
        }
        if (!productRepository.existsById(dto.getProductId())) {
            throw new EntityNotFoundException("해당 제품을 찾을 수 없습니다.");
        }
        if (!employeeRepository.existsById(dto.getRequesterId())) {
            throw new EntityNotFoundException("해당 요청자를 찾을 수 없습니다.");
        }
        if (!departmentRepository.existsById(dto.getDepartmentId())) {
            throw new EntityNotFoundException("해당 부서를 찾을 수 없습니다.");
        }
    }

    @Override
    public ProductionRequestDetailDTO getProductionRequestById(Long id) {
        ProductionRequest entity = productionRequestsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("생산 요청을 찾을 수 없습니다."));
        return ProductionRequestDetailDTO.fromEntity(entity);
    }

    @Override
    public List<ProductionRequestListDTO> getAllProductionRequests() {
        return productionRequestsRepository.findAll().stream()
                .map(ProductionRequestListDTO::fromEntity)
                .toList();
    }

    @Override
    public ProductionRequestDetailDTO updateProductionRequest(Long id, ProductionRequestDetailDTO dto) {
        ProductionRequest existingEntity = productionRequestsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("생산 요청을 찾을 수 없습니다."));

        // DTO 값을 엔티티에 업데이트
        existingEntity.setName(dto.getName());
        existingEntity.setIsConfirmed(dto.getIsConfirmed());
        existingEntity.setRequestDate(LocalDate.parse(dto.getRequestDate()));
        existingEntity.setRequestQuantity(dto.getRequestQuantity());
        existingEntity.setConfirmedQuantity(dto.getConfirmedQuantity());

        // 연관된 엔티티들 업데이트
        updateRelatedEntities(existingEntity, dto);

        ProductionRequest updatedEntity = productionRequestsRepository.save(existingEntity);
        return ProductionRequestDetailDTO.fromEntity(updatedEntity);
    }

    private void updateRelatedEntities(ProductionRequest request, ProductionRequestDetailDTO dto) {
        if (dto.getClientId() != null) {
            Client client = clientRepository.findById(dto.getClientId())
                    .orElseThrow(() -> new EntityNotFoundException("클라이언트를 찾을 수 없습니다."));
            request.setClient(client);
        }

        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new EntityNotFoundException("부서를 찾을 수 없습니다."));
            request.setProductionDepartment(department);
        }

        if (dto.getProductId() != null) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("제품을 찾을 수 없습니다."));
            request.setProduct(product);
        }

        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new EntityNotFoundException("부서를 찾을 수 없습니다."));
            request.setProductionDepartment(department);
            System.out.println("부서가 설정되었습니다: " + department.getDepartmentName());

            System.out.println("부서 이름: " + dto.getDepartmentName());
            System.out.println("부서 코드: " + dto.getDepartmentCode());

        } else {
            System.out.println("부서 ID가 전달되지 않았습니다.");
        }

    }

    @Override
    public void deleteProductionRequest(Long id) {
        ProductionRequest entity = productionRequestsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("생산 요청을 찾을 수 없습니다."));

        if (entity.getIsConfirmed()) {
            throw new IllegalStateException("확정된 생산 요청은 삭제할 수 없습니다.");
        }

        productionRequestsRepository.delete(entity);
    }

    @Override
    public ProductionRequestDetailDTO confirmProductionRequest(Long id, Long confirmedQuantity) {
        ProductionRequest request = productionRequestsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("생산 요청을 찾을 수 없습니다."));

        if (request.getIsConfirmed()) {
            throw new IllegalStateException("이미 확정된 생산 요청입니다.");
        }

        if (request.getDeadlineOfCompletion() != null &&
                request.getDueDateToProvide() != null &&
                request.getDueDateToProvide().isBefore(request.getDeadlineOfCompletion())) {
            throw new IllegalArgumentException("납기일이 완료 요청일자보다 빠를 수 없습니다.");
        }

        if (confirmedQuantity != null && confirmedQuantity > request.getRequestQuantity()) {
            throw new IllegalArgumentException("확정 수량이 요청 수량을 초과할 수 없습니다.");
        }

        request.setConfirmedQuantity(confirmedQuantity);
        request.setIsConfirmed(true);
        request.setProgressType(ProgressType.NOT_STARTED);

        // MPS 자동 생성
        // mpsService.createMps(request);

        ProductionRequest updatedRequest = productionRequestsRepository.save(request);
        return ProductionRequestDetailDTO.fromEntity(updatedRequest);
    }

    @Override
    public void updateProgress(Long requestId, ProgressType progressType) {
        ProductionRequest request = productionRequestsRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("생산 요청을 찾을 수 없습니다."));
        request.setProgressType(progressType);
        productionRequestsRepository.save(request);
    }
}
