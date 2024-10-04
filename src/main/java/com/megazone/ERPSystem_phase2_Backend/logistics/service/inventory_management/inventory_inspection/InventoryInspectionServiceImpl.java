package com.megazone.ERPSystem_phase2_Backend.logistics.service.inventory_management.inventory_inspection;

import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.Inventory;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.InventoryAdjustment;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.InventoryAdjustmentDetail;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.InventoryInspection;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.InventoryInspectionDetail;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.dto.*;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.enums.InspectionStatus;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.inventory.InventoryRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.inventory_adjustment.InventoryAdjustmentRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.inventory_inspection.InventoryInspectionRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryInspectionServiceImpl implements InventoryInspectionService {

    private final InventoryInspectionRepository inspectionRepository;
    private final InventoryRepository inventoryRepository;
    private final InventoryAdjustmentRepository inventoryAdjustmentRepository;
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

        List<InventoryInspectionDetail> details = requestDTO.getDetails().stream().map(item -> {
            Inventory inventory = inventoryRepository.findById(item.getInventoryId()).orElseThrow(() -> new IllegalArgumentException("재고 정보를 찾을 수 없습니다."));
            Product product = productRepository.findById(item.getId()).orElseThrow(() -> new IllegalArgumentException("품목 정보를 찾을 수 없습니다."));
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

        Long newInspectionNumber;
        if (!existingInspection.getInspectionDate().equals(updateDTO.getInspectionDate())) {
            Long maxInspectionNumber = inspectionRepository.findMaxInspectionNumberByDate(updateDTO.getInspectionDate());
            newInspectionNumber = (maxInspectionNumber == null) ? 1 : maxInspectionNumber + 1;
        } else {
            newInspectionNumber = existingInspection.getInspectionNumber();
        }

        InventoryInspection updatedInspection = InventoryInspection.builder()
                .id(existingInspection.getId())
                .inspectionDate(updateDTO.getInspectionDate())
                .inspectionNumber(newInspectionNumber)
                .warehouse(warehouseRepository.findById(updateDTO.getWarehouseId()).orElseThrow(() -> new IllegalArgumentException("창고 정보를 찾을 수 없습니다.")))
                .employee(employeeRepository.findById(updateDTO.getEmployeeId()).orElseThrow(() -> new IllegalArgumentException("직원 정보를 찾을 수 없습니다.")))
                .status(existingInspection.getStatus())
                .comment(updateDTO.getComment())
                .details(existingInspection.getDetails())
                .build();

        existingInspection.getDetails().clear();

        List<InventoryInspectionDetail> updatedDetails = updateDTO.getDetails().stream().map(details -> {
            Inventory inventory = inventoryRepository.findById(details.getInventoryId()).orElseThrow(() -> new IllegalArgumentException("재고 정보를 찾을 수 없습니다."));
            Product product = productRepository.findById(details.getId()).orElseThrow(() -> new IllegalArgumentException("품목 정보를 찾을 수 없습니다."));

            return InventoryInspectionDetail.builder()
                    .inventoryInspection(updatedInspection)
                    .product(product)
                    .inventory(inventory)
                    .warehouseLocation(inventory.getWarehouseLocation())
                    .productCode(details.getProductCode())
                    .productName(details.getProductName())
                    .standard(product.getStandard())
                    .unit(product.getUnit())
                    .bookQuantity(null)
                    .actualQuantity(details.getActualQuantity())
                    .differenceQuantity(0L)
                    .comment(details.getComment())
                    .build();
        }).collect(Collectors.toList());

        updatedInspection.getDetails().addAll(updatedDetails);
        InventoryInspection savedInspection = inspectionRepository.save(updatedInspection);

        return mapToResponseDTO(savedInspection);
    }

    @Override
    public void deleteInspectionById(Long id) {
        InventoryInspection inspection = inspectionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 재고 실사를 찾을 수 없습니다. ID: " + id));

        inspectionRepository.delete(inspection);
    }

    @Override
    public InventoryAdjustmentResponseDTO adjustInventoryByInspection(Long id) {
        InventoryInspection inspection = inspectionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID에 대한 실사 데이터를 찾을 수 없습니다."));

        if (inspection.getStatus() != InspectionStatus.미조정) {
            throw new IllegalArgumentException("이미 조정이 진행된 실사 데이터입니다.");
        }

        List<InventoryAdjustmentDetail> adjustmentDetails = new ArrayList<>();
        for (InventoryInspectionDetail detail : inspection.getDetails()) {
            Inventory inventory = detail.getInventory();

            Inventory updatedInventory = Inventory.builder()
                    .id(inventory.getId())
                    .quantity(detail.getActualQuantity())
                    .product(inventory.getProduct())
                    .warehouseLocation(inventory.getWarehouseLocation())
                    .warehouse(inventory.getWarehouse())
                    .standard(inventory.getStandard())
                    .build();

            inventoryRepository.save(updatedInventory);

            InventoryAdjustmentDetail adjustmentDetail = InventoryAdjustmentDetail.builder()
                    .product(detail.getProduct())
                    .productCode(detail.getProductCode())
                    .productName(detail.getProductName())
                    .standard(detail.getStandard())
                    .unit(detail.getUnit())
                    .bookQuantity(detail.getBookQuantity())
                    .adjustmentQuantity(detail.getActualQuantity())
                    .comment("재고 실사 조정")
                    .build();
            adjustmentDetails.add(adjustmentDetail);
        }

        InventoryAdjustment adjustment = InventoryAdjustment.builder()
                .inventoryInspection(inspection)
                .employee(inspection.getEmployee())
                .adjustmentDate(LocalDate.now())
                .adjustmentDetails(new ArrayList<>())
                .comment("재고 실사 조정 완료")
                .build();

        List<InventoryAdjustmentDetail> finalAdjustmentDetails = adjustmentDetails.stream()
                .map(detail -> InventoryAdjustmentDetail.builder()
                        .id(detail.getId())  // 기존 ID 유지
                        .product(detail.getProduct())
                        .productCode(detail.getProductCode())
                        .productName(detail.getProductName())
                        .standard(detail.getStandard())
                        .unit(detail.getUnit())
                        .bookQuantity(detail.getBookQuantity())
                        .adjustmentQuantity(detail.getAdjustmentQuantity())
                        .comment(detail.getComment())
                        .inventoryAdjustment(adjustment)
                        .build())
                .collect(Collectors.toList());

        InventoryAdjustment finalAdjustment = InventoryAdjustment.builder()
                .id(adjustment.getId())
                .inventoryInspection(adjustment.getInventoryInspection())
                .employee(adjustment.getEmployee())
                .adjustmentDate(adjustment.getAdjustmentDate())
                .adjustmentDetails(finalAdjustmentDetails)
                .comment(adjustment.getComment())
                .build();

        inventoryAdjustmentRepository.save(finalAdjustment);

        InventoryInspection updatedInspection = InventoryInspection.builder()
                .id(inspection.getId())
                .warehouse(inspection.getWarehouse())
                .employee(inspection.getEmployee())
                .details(inspection.getDetails())
                .inspectionDate(inspection.getInspectionDate())
                .inspectionNumber(inspection.getInspectionNumber())
                .status(InspectionStatus.조정완료)
                .comment(inspection.getComment())
                .build();

        inspectionRepository.save(updatedInspection);

        return InventoryAdjustmentResponseDTO.mapToDTO(finalAdjustment);
    }

    private InventoryInspectionResponseDTO mapToResponseDTO(InventoryInspection inspection) {
        return new InventoryInspectionResponseDTO(
                inspection.getId(),
                inspection.getInspectionDate().toString(),
                inspection.getInspectionNumber(),
                inspection.getEmployee().getLastName() + inspection.getEmployee().getFirstName(),
                inspection.getWarehouse().getName(),
                inspection.getStatus(),
                inspection.getComment(),
                inspection.getDetails().stream()
                        .map(detail -> new InventoryInspectionDetailResponseDTO(
                                detail.getId(),
                                detail.getWarehouseLocation().getLocationName(),
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
                        detail.getBookQuantity(),
                        detail.getActualQuantity(),
                        detail.getDifferenceQuantity(),
                        detail.getComment()
                )).collect(Collectors.toList())
        );
    }
}
