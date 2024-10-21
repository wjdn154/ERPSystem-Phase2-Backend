package com.megazone.ERPSystem_phase2_Backend.logistics.service.inventory_management.inventory_inspection;

import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.Inventory;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.InventoryInspection;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.InventoryInspectionDetail;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.dto.*;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.enums.InspectionStatus;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.inventory.InventoryRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.inventory_inspection.InventoryInspectionRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryInspectionServiceImpl implements InventoryInspectionService {

    private final InventoryInspectionRepository inspectionRepository;
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<InventoryInspectionResponseListDTO> getInspectionsByDateRange(LocalDate startDate, LocalDate endDate) {
        List<InventoryInspection> inspections = inspectionRepository.findInspectionsByDateRange(startDate, endDate);

        return inspections.stream()
                .map(this::mapToDto)  // mapToDto 메서드로 변환
                .collect(Collectors.toList());
    }

    @Override
    public InventoryInspectionResponseDTO getInspectionById(Long id) {
        InventoryInspection inspection = inspectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 ID의 재고 실사를 찾을 수 없습니다. ID: " + id));

        return InventoryInspectionResponseDTO.mapToDto(inspection);
    }

    @Override
    public InventoryInspectionResponseDTO createInventoryInspection(InventoryInspectionRequestDTO requestDTO) {
        Long maxInspectionNumber = inspectionRepository.findMaxInspectionNumberByDate(requestDTO.getInspectionDate());
        Long nextInspectionNumber = (maxInspectionNumber == null) ? 1 : maxInspectionNumber + 1;

        InventoryInspection inventoryInspection = InventoryInspection.builder()
                .inspectionDate(requestDTO.getInspectionDate())
                .inspectionNumber(nextInspectionNumber)
                .warehouse(warehouseRepository.findById(requestDTO.getWarehouseId()).orElseThrow(() -> new IllegalArgumentException("창고 정보를 찾을 수 없습니다.")))
                .employee(employeeRepository.findById(requestDTO.getEmployeeId()).orElseThrow(() -> new IllegalArgumentException("직원 정보를 찾을 수 없습니다.")))
                .status(InspectionStatus.미조정)
                .comment(requestDTO.getComment())
                .build();

        // 중복 재고 실사 항목 방지 로직 추가
        Set<Long> inspectedInventoryIds = new HashSet<>();

        List<InventoryInspectionDetail> details = requestDTO.getDetails().stream().map(item -> {
            if (inspectedInventoryIds.contains(item.getInventoryId())) {
                throw new IllegalArgumentException("동일한 재고에 대한 중복 실사가 발생했습니다. 재고 ID: " + item.getInventoryId());
            }

            inspectedInventoryIds.add(item.getInventoryId()); // 처리된 재고 ID를 추가

            Inventory inventory = inventoryRepository.findById(item.getInventoryId()).orElseThrow(() -> new IllegalArgumentException("재고 정보를 찾을 수 없습니다."));
            Product product = productRepository.findById(item.getProductId()).orElseThrow(() -> new IllegalArgumentException("품목 정보를 찾을 수 없습니다."));
            return InventoryInspectionDetail.builder()
                    .inventoryInspection(inventoryInspection)
                    .product(product)
                    .inventory(inventory)
                    .warehouseLocation(inventory.getWarehouseLocation())
                    .productCode(item.getProductCode())
                    .productName(item.getProductName())
                    .standard(product.getStandard())
                    .unit(product.getUnit())
                    .bookQuantity(null)
                    .actualQuantity(item.getActualQuantity())
                    .differenceQuantity(null)
                    .comment(item.getComment())
                    .build();
        }).collect(Collectors.toList());

        inventoryInspection.getDetails().addAll(details);

        InventoryInspection savedInspection = inspectionRepository.save(inventoryInspection);

        return mapToResponseDTO(savedInspection);
    }


    @Override
    public InventoryInspectionResponseDTO updateInventoryInspection(Long id, InventoryInspectionRequestDTO updateDTO) {
        InventoryInspection existingInspection = inspectionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 재고 실사를 찾을 수 없습니다."));

        // 실사 날짜가 변경된 경우 새로운 실사 번호 생성
        Long newInspectionNumber;
        if (!existingInspection.getInspectionDate().equals(updateDTO.getInspectionDate())) {
            Long maxInspectionNumber = inspectionRepository.findMaxInspectionNumberByDate(updateDTO.getInspectionDate());
            newInspectionNumber = (maxInspectionNumber == null) ? 1 : maxInspectionNumber + 1;
        } else {
            newInspectionNumber = existingInspection.getInspectionNumber();
        }

        // 기존 실사 항목 삭제 후 업데이트된 항목 추가
        existingInspection.getDetails().clear();

        // 새로운 실사 정보로 업데이트
        InventoryInspection updatedInspection = InventoryInspection.builder()
                .id(existingInspection.getId()) // 기존 ID 유지
                .inspectionDate(updateDTO.getInspectionDate()) // 업데이트된 실사 날짜
                .inspectionNumber(newInspectionNumber) // 새로운 실사 번호
                .warehouse(warehouseRepository.findById(updateDTO.getWarehouseId())
                        .orElseThrow(() -> new IllegalArgumentException("창고 정보를 찾을 수 없습니다.")))
                .employee(employeeRepository.findById(updateDTO.getEmployeeId())
                        .orElseThrow(() -> new IllegalArgumentException("직원 정보를 찾을 수 없습니다.")))
                .status(existingInspection.getStatus()) // 상태는 그대로 유지
                .comment(updateDTO.getComment()) // 업데이트된 비고
                .build();

        // 실사 항목 업데이트
        List<InventoryInspectionDetail> updatedDetails = updateDTO.getDetails().stream().map(detailDTO -> {
            // 기존 재고 정보 조회
            Inventory inventory = inventoryRepository.findById(detailDTO.getInventoryId())
                    .orElseThrow(() -> new IllegalArgumentException("재고 정보를 찾을 수 없습니다."));
            Product product = productRepository.findById(detailDTO.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("품목 정보를 찾을 수 없습니다."));

            // 기존 실사 항목이 있는지 확인 (존재하면 ID 유지)
            InventoryInspectionDetail existingDetail = existingInspection.getDetails().stream()
                    .filter(detail -> detail.getProduct().getId().equals(product.getId()))
                    .findFirst()
                    .orElse(null);

            Long detailId = (existingDetail != null) ? existingDetail.getId() : null;

            // 실사 항목 업데이트
            return InventoryInspectionDetail.builder()
                    .id(detailId)  // 기존 ID 유지
                    .inventoryInspection(updatedInspection) // 새롭게 연결된 실사 정보
                    .inventory(inventory) // 업데이트된 재고 정보
                    .product(product) // 업데이트된 품목 정보
                    .warehouseLocation(inventory.getWarehouseLocation()) // 기존 창고 위치 유지
                    .productCode(detailDTO.getProductCode()) // 업데이트된 제품 코드
                    .productName(detailDTO.getProductName()) // 업데이트된 제품 이름
                    .standard(product.getStandard()) // 기존 규격 유지
                    .unit(product.getUnit()) // 기존 단위 유지
                    .bookQuantity(null) // 장부 수량은 업데이트 전에 null로 설정
                    .actualQuantity(detailDTO.getActualQuantity()) // 업데이트된 실사 수량
                    .differenceQuantity(0L) // 차이 수량 초기화
                    .comment(detailDTO.getComment()) // 업데이트된 비고
                    .build();
        }).collect(Collectors.toList());

        // 실사 항목 업데이트
        updatedInspection.getDetails().addAll(updatedDetails);

        // 실사 정보 저장
        InventoryInspection savedInspection = inspectionRepository.save(updatedInspection);

        return mapToResponseDTO(savedInspection);
    }


    @Override
    public void deleteInspectionById(Long id) {
        InventoryInspection inspection = inspectionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 재고 실사를 찾을 수 없습니다. ID: " + id));

        inspectionRepository.delete(inspection);
    }

    /**
     * 재고 실사 조정
     * 사용자가 선택한 재고 실사에 대해 입력한 실사 수량대로 장부 수량 데이터를 업데이트
     * @param id 재고 실사 ID
     */
    @Override
    public void adjustRequest(Long id) {
        // 1. 실사 정보 조회
        InventoryInspection inspection = inspectionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 재고 실사를 찾을 수 없습니다. ID: " + id));

        // 2. 실사 항목(InventoryInspectionDetail)을 순회하며 각 항목의 수량을 조정
        List<InventoryInspectionDetail> updatedDetails = inspection.getDetails().stream().map(detail -> {
            Inventory inventory = inventoryRepository.findById(detail.getInventory().getId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 ID의 재고를 찾을 수 없습니다. ID: " + detail.getInventory().getId()));

            // 3. 새로운 InventoryInspectionDetail 객체를 생성하며 장부 수량(bookQuantity)을 설정
            InventoryInspectionDetail updatedDetail = InventoryInspectionDetail.builder()
                    .id(detail.getId())  // 기존 ID 유지
                    .inventoryInspection(detail.getInventoryInspection())  // 기존 실사 정보 유지
                    .inventory(detail.getInventory())  // 기존 재고 정보 유지
                    .product(detail.getProduct())  // 기존 품목 정보 유지
                    .warehouseLocation(detail.getWarehouseLocation())  // 기존 창고 위치 유지
                    .productCode(detail.getProductCode())  // 기존 제품 코드 유지
                    .productName(detail.getProductName())  // 기존 제품 이름 유지
                    .standard(detail.getStandard())  // 기존 규격 유지
                    .unit(detail.getUnit())  // 기존 단위 유지
                    .bookQuantity(inventory.getQuantity())  // 현재 장부 수량 설정
                    .actualQuantity(detail.getActualQuantity())  // 기존 실사 수량 유지
                    .differenceQuantity(detail.getActualQuantity() - inventory.getQuantity())  // 차이 수량 계산
                    .comment(detail.getComment())  // 기존 비고 유지
                    .build();

            // 4. 새로운 Inventory 객체 생성 (수량 업데이트)
            Inventory updatedInventory = Inventory.builder()
                    .id(inventory.getId())  // 기존 ID 유지
                    .warehouse(inventory.getWarehouse())  // 기존 창고 유지
                    .product(inventory.getProduct())  // 기존 품목 유지
                    .warehouseLocation(inventory.getWarehouseLocation())  // 기존 창고 위치 유지
                    .inventoryNumber(inventory.getInventoryNumber())  // 기존 재고번호 유지
                    .standard(inventory.getStandard())  // 기존 규격 유지
                    .quantity(updatedDetail.getActualQuantity())  // 실사 수량으로 재고 수량 업데이트
                    .build();

            // 5. 재고 수량 업데이트
            inventoryRepository.save(updatedInventory);

            return updatedDetail;  // 업데이트된 상세 정보 반환
        }).collect(Collectors.toList());

        // 6. 조정 전표(adjustmentNumber) 생성 (형식: YYYY/MM/DD-InspectionNumber)
        String adjustmentNumber = String.format("%s - %d",
                inspection.getInspectionDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                inspection.getInspectionNumber());

        // 6. 새로운 InventoryInspection 객체 생성 (상태 업데이트)
        InventoryInspection updatedInspection = InventoryInspection.builder()
                .id(inspection.getId())  // 기존 ID 유지
                .inspectionDate(inspection.getInspectionDate())  // 기존 실사 날짜 유지
                .inspectionNumber(inspection.getInspectionNumber())  // 기존 실사 번호 유지
                .adjustmentSlip(adjustmentNumber)  // 조정 전표 번호 설정
                .warehouse(inspection.getWarehouse())  // 기존 창고 유지
                .employee(inspection.getEmployee())  // 기존 직원 유지
                .status(InspectionStatus.조정완료)  // 상태를 '조정 완료'로 변경
                .comment(inspection.getComment())  // 기존 비고 유지
                .details(updatedDetails)  // 업데이트된 실사 항목 리스트
                .build();

        // 7. 실사 정보 업데이트 후 저장
        inspectionRepository.save(updatedInspection);
    }

    private InventoryInspectionResponseDTO mapToResponseDTO(InventoryInspection inspection) {
        return new InventoryInspectionResponseDTO(
                inspection.getId(),
                inspection.getInspectionDate().toString(),
                inspection.getInspectionNumber(),
                inspection.getEmployee().getId(),
                inspection.getEmployee().getLastName() + inspection.getEmployee().getFirstName(),
                inspection.getWarehouse().getId(),
                inspection.getWarehouse().getName(),
                inspection.getStatus(),
                inspection.getComment(),
                inspection.getDetails().stream()
                        .map(detail -> new InventoryInspectionDetailResponseDTO(
                                detail.getId(),
                                detail.getInventory().getId(),
                                detail.getWarehouseLocation().getId(),
                                detail.getWarehouseLocation().getLocationName(),
                                detail.getProduct().getId(),
                                detail.getProductCode(),
                                detail.getProductName(),
                                detail.getProduct().getStandard(),
                                detail.getProduct().getUnit(),
                                detail.getActualQuantity(),
                                detail.getComment()
                        ))
                        .collect(Collectors.toList())
        );
    }

    private InventoryInspectionResponseListDTO mapToDto(InventoryInspection inspection) {
        String productSummary = inspection.getDetails().isEmpty()
                ? "품목 없음"
                : inspection.getDetails().get(0).getProduct().getName() + " 외 " + (inspection.getDetails().size() - 1) + "건";

        Long totalBookQuantity = inspection.getDetails() != null ?
                inspection.getDetails().stream()
                        .filter(detail -> detail.getBookQuantity() != null)  // null 값인 항목을 제외
                        .mapToLong(InventoryInspectionDetail::getBookQuantity)
                        .sum() : null;  //

        Long totalActualQuantity = inspection.getDetails() != null ?
                inspection.getDetails().stream()
                        .mapToLong(detail -> detail.getActualQuantity() != null ? detail.getActualQuantity() : 0L)
                        .sum() : null;

        Long totalDifferenceQuantity = inspection.getDetails() != null ?
                inspection.getDetails().stream()
                        .mapToLong(detail -> detail.getDifferenceQuantity() != null ? detail.getDifferenceQuantity() : 0L)
                        .sum() : null;

        return new InventoryInspectionResponseListDTO(
                inspection.getId(),
                inspection.getInspectionDate().toString() + " - " + inspection.getInspectionNumber(),
                inspection.getInspectionDate().toString(),
                inspection.getAdjustmentSlip(),
                inspection.getEmployee().getLastName() + inspection.getEmployee().getFirstName(),
                productSummary,
                inspection.getStatus(),
                inspection.getWarehouse().getName(),
                totalBookQuantity,
                totalActualQuantity,
                totalDifferenceQuantity,
                inspection.getDetails().stream().map(detail -> new InventoryInspectionDetailResponseListDTO(
                        detail.getId(),
                        detail.getProductCode(),
                        detail.getProductName(),
                        detail.getStandard(),
                        detail.getBookQuantity(),
                        detail.getActualQuantity(),
                        detail.getDifferenceQuantity(),
                        detail.getComment()
                )).collect(Collectors.toList())
        );
    }
}
