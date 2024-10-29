package com.megazone.ERPSystem_phase2_Backend.logistics.service.shipping_processing_management;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory.Inventory;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.SaleState;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.ShippingOrder;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.ShippingOrderDetail;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipping_processing_management.ShippingProcessing;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipping_processing_management.dto.ShippingProcessingRequestDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipping_processing_management.dto.ShippingProcessingResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.shipping_processing_management.enums.ShippingStatus;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.inventory_management.inventory.InventoryRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.sales_management.shipping_order.ShippingOrderRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.sales_management.shipping_order_details.ShippingOrderDetailRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.shipping_processing_management.ShippingProcessingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ShippingProcessingServiceImpl implements ShippingProcessingService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final ShippingOrderDetailRepository shippingOrderDetailRepository;
    private final ShippingProcessingRepository shippingProcessingRepository;
    private final ShippingOrderRepository shippingOrderRepository;

    @Override
    public void registerShippingProcessing(ShippingProcessingRequestDTO requestDTO) {
        Inventory inventory = inventoryRepository.findById(requestDTO.getInventoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 재고 ID입니다."));

        Product product = productRepository.findById(requestDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 제품 ID입니다."));

        ShippingOrderDetail shippingOrderDetail = shippingOrderDetailRepository.findById(requestDTO.getShippingOrderDetailId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 출하 지시서 상세 ID입니다."));

        if (shippingOrderDetail.getQuantity() > inventory.getQuantity()) {
            throw new IllegalArgumentException("재고가 부족합니다. 요청된 수량: " + shippingOrderDetail.getQuantity() + ", 현재 재고: " + inventory.getQuantity());
        }

        // shippingDate에 대한 최대 shippingNumber 조회
        Long maxShippingNumber = shippingProcessingRepository.getMaxShippingNumberByDate(LocalDate.parse(requestDTO.getShippingDate()));
        Long shippingNumber = (maxShippingNumber != null) ? maxShippingNumber + 1 : 1L;

        ShippingProcessing shippingProcessing = ShippingProcessing.builder()
                .inventory(inventory)
                .product(product)
                .shippingOrderDetail(shippingOrderDetail)
                .shippingDate(LocalDate.parse(requestDTO.getShippingDate()))
                .shippingNumber(shippingNumber)
                .productName(requestDTO.getProductName())
                .shippingInventoryNumber(requestDTO.getShippingInventoryNumber())
                .shippingStatus(ShippingStatus.WAITING_FOR_SHIPMENT)
                .build();

        shippingProcessingRepository.save(shippingProcessing);

        ShippingOrderDetail updatedShippingOrderDetail = ShippingOrderDetail.builder()
                .id(shippingOrderDetail.getId())
                .shippingOrder(shippingOrderDetail.getShippingOrder())
                .product(shippingOrderDetail.getProduct())
                .quantity(shippingOrderDetail.getQuantity())
                .remarks(shippingOrderDetail.getRemarks())
                .shippingStatus(ShippingStatus.WAITING_FOR_SHIPMENT)
                .build();

        shippingOrderDetailRepository.save(updatedShippingOrderDetail);

        ShippingOrder shippingOrder = shippingOrderRepository.findById(updatedShippingOrderDetail.getShippingOrder().getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 입고 지시입니다."));

        ShippingOrder updatedShippingOrder = ShippingOrder.builder()
                .id(shippingOrder.getId())
                .shippingOrderDetails(shippingOrder.getShippingOrderDetails())
                .client(shippingOrder.getClient())
                .manager(shippingOrder.getManager())
                .shippingWarehouse(shippingOrder.getShippingWarehouse())
                .shippingAddress(shippingOrder.getShippingAddress())
                .postalCode(shippingOrder.getPostalCode())
                .shippingDate(shippingOrder.getShippingDate())
                .date(shippingOrder.getDate())
                .remarks(shippingOrder.getRemarks())
                .state(SaleState.IN_PROGRESS)
                .build();

        shippingOrderRepository.save(updatedShippingOrder);
    }

    @Override
    public List<ShippingProcessingResponseDTO> getShippingProcessingList(LocalDate startDate, LocalDate endDate) {
        return shippingProcessingRepository.findShippingProcessingByDateRangeAndStatus(startDate, endDate)
                .stream()
                .map(ShippingProcessingResponseDTO::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void processShipping(Long shippingProcessingId) {
        // ShippingProcessing 데이터 조회
        ShippingProcessing shippingProcessing = shippingProcessingRepository.findById(shippingProcessingId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 출고 처리 ID입니다."));

        // Inventory와 ShippingOrderDetail 데이터 확인
        Inventory inventory = shippingProcessing.getInventory();
        ShippingOrderDetail shippingOrderDetail = shippingProcessing.getShippingOrderDetail();

        // 수량 확인
        if (inventory.getQuantity() < shippingOrderDetail.getQuantity()) {
            throw new IllegalArgumentException("재고 수량이 부족하여 출고할 수 없습니다.");
        }

        // 재고 수량 차감
        Long updatedQuantity = inventory.getQuantity() - shippingOrderDetail.getQuantity();
        inventory = Inventory.builder()
                .id(inventory.getId())
                .warehouse(inventory.getWarehouse())
                .product(inventory.getProduct())
                .warehouseLocation(inventory.getWarehouseLocation())
                .inventoryNumber(inventory.getInventoryNumber())
                .standard(inventory.getStandard())
                .quantity(updatedQuantity)
                .build();
        inventoryRepository.save(inventory);

        // 상태 업데이트
        shippingProcessing = ShippingProcessing.builder()
                .id(shippingProcessing.getId())
                .inventory(shippingProcessing.getInventory())
                .product(shippingProcessing.getProduct())
                .shippingOrderDetail(shippingProcessing.getShippingOrderDetail())
                .shippingDate(shippingProcessing.getShippingDate())
                .shippingNumber(shippingProcessing.getShippingNumber())
                .productName(shippingProcessing.getProductName())
                .shippingInventoryNumber(shippingProcessing.getShippingInventoryNumber())
                .shippingStatus(ShippingStatus.SHIPPED)
                .build();
        shippingProcessingRepository.save(shippingProcessing);

        // ShippingOrderDetail 상태 업데이트
        shippingOrderDetail = ShippingOrderDetail.builder()
                .id(shippingOrderDetail.getId())
                .shippingOrder(shippingOrderDetail.getShippingOrder())
                .product(shippingOrderDetail.getProduct())
                .quantity(shippingOrderDetail.getQuantity())
                .remarks(shippingOrderDetail.getRemarks())
                .shippingStatus(ShippingStatus.SHIPPED)
                .build();
        shippingOrderDetailRepository.save(shippingOrderDetail);

        // ShippingOrder의 모든 Detail 상태가 SHIPPED인지 확인하여 COMPLETED 상태로 변경
        ShippingOrder shippingOrder = shippingOrderDetail.getShippingOrder();
        boolean allShipped = shippingOrder.getShippingOrderDetails().stream()
                .allMatch(detail -> detail.getShippingStatus() == ShippingStatus.SHIPPED);
        if (allShipped) {
            shippingOrder = ShippingOrder.builder()
                    .id(shippingOrder.getId())
                    .shippingOrderDetails(shippingOrder.getShippingOrderDetails())
                    .client(shippingOrder.getClient())
                    .manager(shippingOrder.getManager())
                    .shippingWarehouse(shippingOrder.getShippingWarehouse())
                    .shippingAddress(shippingOrder.getShippingAddress())
                    .postalCode(shippingOrder.getPostalCode())
                    .shippingDate(shippingOrder.getShippingDate())
                    .date(shippingOrder.getDate())
                    .remarks(shippingOrder.getRemarks())
                    .state(SaleState.COMPLETED)
                    .build();
            shippingOrderRepository.save(shippingOrder);
        }
    }

}
