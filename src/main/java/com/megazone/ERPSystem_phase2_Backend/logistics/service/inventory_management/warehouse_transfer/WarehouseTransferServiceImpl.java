package com.megazone.ERPSystem_phase2_Backend.logistics.service.inventory_management.warehouse_transfer;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.WarehouseTransfer;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.WarehouseTransferProduct;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.dto.WarehouseTransferProductResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.dto.WarehouseTransferRequestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.dto.WarehouseTransferResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.dto.WarehouseTransferResponseListDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_transfer.enums.TransferStatus;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.warehouse_transfer.WarehouseTransferProductRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.warehouse_transfer.WarehouseTransferRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WarehouseTransferServiceImpl implements WarehouseTransferService {

    private final WarehouseTransferRepository warehouseTransferRepository;
    private final WarehouseTransferProductRepository warehouseTransferProductRepository;
    private final WarehouseRepository warehouseRepository;
    private final EmployeeRepository employeeRepository;
    private final ProductRepository productRepository;

    @Override
    public List<WarehouseTransferResponseListDTO> findTransfers(LocalDate startDate, LocalDate endDate) {
        return warehouseTransferRepository.findTransfers(startDate, endDate);
    }

    @Override
    public Optional<WarehouseTransferResponseDTO> getTransferDetail(Long transferId) {
        return warehouseTransferRepository.findTransferDetailById(transferId);
    }

    @Override
    public WarehouseTransferResponseDTO createWarehouseTransfer(WarehouseTransferRequestDTO requestDTO) {
        // 동일 날짜의 다음 이동 번호를 계산
        Long transferNumber = warehouseTransferRepository.getNextTransferNumber(requestDTO.getTransferDate());

        // Warehouse 및 Employee 엔티티를 리포지토리에서 조회하여 가져옴
        Warehouse sendingWarehouse = warehouseRepository.findById(requestDTO.getSendingWarehouseId())
                .orElseThrow(() -> new IllegalArgumentException("보내는 창고를 찾을 수 없습니다."));
        Warehouse receivingWarehouse = warehouseRepository.findById(requestDTO.getReceivingWarehouseId())
                .orElseThrow(() -> new IllegalArgumentException("받는 창고를 찾을 수 없습니다."));
        Employee employee = employeeRepository.findById(requestDTO.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("직원을 찾을 수 없습니다."));

        // WarehouseTransfer 엔티티 생성
        WarehouseTransfer transfer = WarehouseTransfer.builder()
                .sendingWarehouse(sendingWarehouse)
                .receivingWarehouse(receivingWarehouse)
                .employee(employee)
                .transferDate(requestDTO.getTransferDate())
                .transferNumber(transferNumber)
                .status(TransferStatus.미확인)  // 기본 상태: 미확인
                .comment(requestDTO.getComment())
                .build();

        // 창고 이동 엔티티 저장
        WarehouseTransfer savedTransfer = warehouseTransferRepository.save(transfer);

        // 각 이동 품목 저장 (Product 엔티티도 리포지토리에서 조회)
        List<WarehouseTransferProduct> products = requestDTO.getProducts().stream()
                .map(productDTO -> {
                    Product product = productRepository.findById(productDTO.getId())
                            .orElseThrow(() -> new IllegalArgumentException("품목을 찾을 수 없습니다."));

                    return WarehouseTransferProduct.builder()
                            .warehouseTransfer(savedTransfer)
                            .product(product)  // 조회된 Product 엔티티 사용
                            .quantity(productDTO.getQuantity())
                            .comment(productDTO.getComment())
                            .build();
                })
                .toList();

        warehouseTransferProductRepository.saveAll(products);

        // 응답 DTO 생성
        return new WarehouseTransferResponseDTO(
                savedTransfer.getId(),
                savedTransfer.getTransferDate(),
                savedTransfer.getTransferNumber().toString(),
                savedTransfer.getSendingWarehouse().getName(),
                savedTransfer.getReceivingWarehouse().getName(),
                savedTransfer.getEmployee().getLastName() + savedTransfer.getEmployee().getFirstName(),
                savedTransfer.getComment(),
                savedTransfer.getStatus(),
                products.stream()
                        .map(product -> new WarehouseTransferProductResponseDTO(
                                product.getProduct().getId(),
                                product.getProduct().getCode(),
                                product.getProduct().getName(),
                                product.getQuantity(),
                                product.getComment()
                        ))
                        .toList()
        );
    }

    @Override
    public WarehouseTransferResponseDTO updateTransfer(Long transferId, WarehouseTransferRequestDTO updateDTO) {
        WarehouseTransfer existingTransfer = warehouseTransferRepository.findById(transferId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 창고 이동입니다."));

        // 날짜가 변경된 경우, 동일 날짜의 창고 이동 번호 중복을 방지하기 위한 처리
        Long transferNumber;
        if (!existingTransfer.getTransferDate().equals(updateDTO.getTransferDate())) {
            // 동일 날짜에 이미 존재하는 다른 창고 이동이 있는지 확인
            Long maxTransferNumber = warehouseTransferRepository.findMaxTransferNumberByDate(updateDTO.getTransferDate());
            if (maxTransferNumber != null) {
                transferNumber = maxTransferNumber + 1;  // 중복을 피하기 위해 최대값 + 1로 설정
            } else {
                transferNumber = existingTransfer.getTransferNumber();  // 중복이 없으면 기존 번호 유지
            }
        } else {
            transferNumber = existingTransfer.getTransferNumber();  // 날짜가 변경되지 않으면 기존 번호 유지
        }

        // 새롭게 빌더 패턴을 사용하여 업데이트된 WarehouseTransfer 객체 생성
        WarehouseTransfer updatedTransfer = WarehouseTransfer.builder()
                .id(existingTransfer.getId())  // 기존 ID 유지
                .transferDate(updateDTO.getTransferDate())
                .transferNumber(transferNumber)  // 계산된 이동 번호 설정
                .comment(updateDTO.getComment())
                .status(updateDTO.getStatus())
                .sendingWarehouse(warehouseRepository.findById(updateDTO.getSendingWarehouseId())
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 보내는 창고입니다.")))
                .receivingWarehouse(warehouseRepository.findById(updateDTO.getReceivingWarehouseId())
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 받는 창고입니다.")))
                .employee(employeeRepository.findById(updateDTO.getEmployeeId())
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 담당자입니다.")))
                .build();

        // 품목 리스트 업데이트 로직 (기존 품목 삭제 후 새로 추가)
        warehouseTransferProductRepository.deleteAllByWarehouseTransfer(existingTransfer);

        List<WarehouseTransferProduct> updatedProducts = updateDTO.getProducts().stream()
                .map(productDTO -> WarehouseTransferProduct.builder()
                        .warehouseTransfer(updatedTransfer)
                        .product(productRepository.findById(productDTO.getId())
                                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 품목입니다.")))
                        .quantity(productDTO.getQuantity())
                        .comment(productDTO.getComment())
                        .build())
                .collect(Collectors.toList());

        // 저장
        warehouseTransferProductRepository.saveAll(updatedProducts);
        warehouseTransferRepository.save(updatedTransfer);

        return new WarehouseTransferResponseDTO(
                updatedTransfer.getId(),
                updatedTransfer.getTransferDate(),
                updatedTransfer.getTransferNumber().toString(),
                updatedTransfer.getSendingWarehouse().getName(),
                updatedTransfer.getReceivingWarehouse().getName(),
                updatedTransfer.getEmployee().getFirstName() + " " + updatedTransfer.getEmployee().getLastName(),
                updatedTransfer.getComment(),
                updatedTransfer.getStatus(),
                updatedProducts.stream().map(product -> new WarehouseTransferProductResponseDTO(
                        product.getProduct().getId(),
                        product.getProduct().getCode(),
                        product.getProduct().getName(),
                        product.getQuantity(),
                        product.getComment()
                )).collect(Collectors.toList())
        );
    }

    @Override
    public void deleteTransfer(Long transferId) {
        // 창고 이동 조회
        WarehouseTransfer existingTransfer = warehouseTransferRepository.findById(transferId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 창고 이동입니다."));

        // 연관된 품목 삭제
        warehouseTransferProductRepository.deleteAllByWarehouseTransfer(existingTransfer);

        // 창고 이동 삭제
        warehouseTransferRepository.delete(existingTransfer);
    }
}
