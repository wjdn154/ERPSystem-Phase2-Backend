package com.megazone.ERPSystem_phase2_Backend.logistics.service.purchase_management.receiving_order;

import com.megazone.ERPSystem_phase2_Backend.Integrated.model.dashboard.RecentActivity;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.dashboard.enums.ActivityType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.ModuleType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.NotificationType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.PermissionType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.repository.dashboard.RecentActivityRepository;
import com.megazone.ERPSystem_phase2_Backend.Integrated.service.notification.NotificationService;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.Client;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.client.ClientRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.ReceivingOrder;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.ReceivingOrderDetail;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.*;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.ReceivingOrderResponseDetailDto.ReceivingOrderItemDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management.CurrencyRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management.receiving_order.ReceivingOrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReceivingOrderServiceImpl implements ReceivingOrderService {

    private final ReceivingOrderRepository receivingOrderRepository;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final WarehouseRepository warehouseRepository;
    private final CurrencyRepository currencyRepository;
    private final ProductRepository productRepository;
    private final RecentActivityRepository recentActivityRepository;
    private final NotificationService notificationService;

    /**
     * 입고지시서 목록 조회
     * @return
     */
    @Override
    public List<ReceivingOrderResponseDto> findAllReceivingOrders(SearchDTO dto) {

        List<ReceivingOrder> receivingOrders;

        // dto가 null이거나 조건이 모두 null일 경우 모든 발주서 조회
        if (dto == null || (dto.getStartDate() == null && dto.getEndDate() == null && dto.getClientId() == null && dto.getState() == null)) {
            receivingOrders = receivingOrderRepository.findAll(); // 전체 발주서 조회
        } else {
            // 조건이 있는 경우 QueryDSL을 사용하여 검색
            receivingOrders = receivingOrderRepository.findBySearch(dto);
        }

        // 발주서가 없는 경우 빈 리스트 반환
        return receivingOrders.isEmpty()
                ? Collections.emptyList()
                : receivingOrders.stream()
                .map(this::toListDto)
                .toList();
    }

    /** 입고지시서 목록 조회 관련 메서드 **/
    // Entity -> 입고지시서 목록 조회용 DTO 변환 메소드
    private ReceivingOrderResponseDto toListDto(ReceivingOrder receivingOrder) {
        return ReceivingOrderResponseDto.builder()
                .id(receivingOrder.getId())
                .clientName(receivingOrder.getClient().getPrintClientName())
                .managerName(receivingOrder.getManager().getLastName() + receivingOrder.getManager().getFirstName())
                .warehouseName(receivingOrder.getReceivingWarehouse().getName())
                .productName(getProductNameWithCount(receivingOrder))
                .totalQuantity(getTotalQuantity(receivingOrder))
                .date(receivingOrder.getDate())
                .deliveryDate(receivingOrder.getDeliveryDate())
                .status(receivingOrder.getStatus().toString())
                .remarks(receivingOrder.getRemarks())
                .build();
    }

    // 총 수량 계산
    private Integer getTotalQuantity(ReceivingOrder receivingOrder) {
        return receivingOrder.getReceivingOrderDetails().stream()
                .mapToInt(ReceivingOrderDetail::getQuantity)
                .sum();
    }

    // 첫 번째 품목 이름과 외 몇건 처리
    private String getProductNameWithCount(ReceivingOrder receivingOrder) {
        List<ReceivingOrderDetail> details = receivingOrder.getReceivingOrderDetails();

        if(details.isEmpty()) {
            return "품목 없음";
        }

        String firstProductName = details.get(0).getProduct().getName();

        if(details.size() > 1) {
            return firstProductName + " 외 " + (details.size() - 1) + "건";
        } else {
            return firstProductName;
        }
    }

    /**
     * 입고지시서 상세 정보 조회
     * @param id
     * @return
     */
    @Override
    public Optional<ReceivingOrderResponseDetailDto> findReceivingOrderDetailById(Long id) {

        return receivingOrderRepository.findById(id)
                .map(this::toDetailDto);
    }


    /** 입고지시서 상세 정보 조회 관련 메서드 **/
    // Entity -> 상세 조회용 DTO 변환 메소드
    private ReceivingOrderResponseDetailDto toDetailDto(ReceivingOrder receivingOrder) {
        return ReceivingOrderResponseDetailDto.builder()
                .id(receivingOrder.getId())
                .clientName(receivingOrder.getClient().getPrintClientName())
                .clientCode(receivingOrder.getClient().getCode())
                .managerCode(receivingOrder.getManager().getEmployeeNumber())
                .managerName(receivingOrder.getManager().getLastName() + receivingOrder.getManager().getFirstName())
                .managerContact(receivingOrder.getManager().getPhoneNumber())
                .warehouseId(receivingOrder.getReceivingWarehouse().getId())
                .warehouseCode(receivingOrder.getReceivingWarehouse().getCode())
                .warehouseName(receivingOrder.getReceivingWarehouse().getName())
                .date(receivingOrder.getDate())
                .deliveryDate(receivingOrder.getDeliveryDate())
                .remarks(receivingOrder.getRemarks())
                .status(receivingOrder.getStatus().toString())
                .receivingOrderDetails(toItemDetailDtoList(receivingOrder.getReceivingOrderDetails()))
                .build();
    }

    private List<ReceivingOrderItemDetailDto> toItemDetailDtoList(List<ReceivingOrderDetail> details) {
        return details.stream()
                .map(this::toItemDetailDto)
                .collect(Collectors.toList());
    }

    private ReceivingOrderItemDetailDto toItemDetailDto(ReceivingOrderDetail detail) {
        Product product = detail.getProduct();
        return ReceivingOrderItemDetailDto.builder()
                .productId(product.getId())
                .productCode(product.getCode())
                .productName(product.getName())
                .standard(product.getStandard())
                .quantity(detail.getQuantity())
                .remarks(detail.getRemarks())
                .build();
    }

    /**
     * 입고지지서 직접 등록
     * @param createDto
     * @return
     */
    @Override
    public ReceivingOrderResponseDetailDto createReceivingOrder(ReceivingOrderCreateDto createDto) {
        try {
            ReceivingOrder receivingOrder = toEntity(createDto);
            receivingOrder = receivingOrderRepository.save(receivingOrder);

            recentActivityRepository.save(RecentActivity.builder()
                    .activityDescription("신규 입고지시서 등록 : " + receivingOrder.getDate() + " -" + receivingOrder.getId())
                    .activityType(ActivityType.LOGISTICS)
                    .activityTime(LocalDateTime.now())
                    .build());
            notificationService.createAndSendNotification(
                    ModuleType.LOGISTICS,
                    PermissionType.USER,
                    "신규 입고지시서 (" + receivingOrder.getDate() + " -" + receivingOrder.getId() + ") 가 등록되었습니다.",
                    NotificationType.NEW_ENTRY
            );
            return toDetailDto(receivingOrder);
        } catch (Exception e) {
            log.error("입고지시서 생성 실패: ", e);
            return null;
        }
    }

    /** 입고지시서 직접 등록 관련 메서드 **/
    // 입고지시서 등록 DTO -> Entity 변환 메소드
    private ReceivingOrder toEntity(ReceivingOrderCreateDto dto) {
        ReceivingOrder newOrder = ReceivingOrder.builder()
                .client(clientRepository.findById(dto.getClientId())
                        .orElseThrow(() -> new RuntimeException("거래처 정보를 찾을 수 없습니다.")))
                .manager(employeeRepository.findById(dto.getManagerId())
                        .orElseThrow(() -> new RuntimeException("담당자 정보를 찾을 수 없습니다.")))
                .receivingWarehouse(warehouseRepository.findById(dto.getWarehouseId())
                        .orElseThrow(() -> new RuntimeException("창고 정보를 찾을 수 없습니다.")))
                .date(dto.getDate())
                .deliveryDate(dto.getDeliveryDate())
                .remarks(dto.getRemarks())
                .build();

        return getReceivingOrder(dto, newOrder);
    }

    private ReceivingOrder getReceivingOrder(ReceivingOrderCreateDto dto, ReceivingOrder newOrder) {
        dto.getItems().forEach(item -> {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("품목 정보를 찾을 수 없습니다."));


            ReceivingOrderDetail detail = ReceivingOrderDetail.builder()
                    .product(product)
                    .quantity(item.getQuantity())
                    .remarks(item.getRemarks())
                    .build();

            newOrder.addReceivingOrderDetail(detail);
        });

        return newOrder;
    }

    /**
     * 입고지시서 수정
     * @param id
     * @param updateDto
     * @return
     */
    @Override
    public ReceivingOrderResponseDetailDto updatePurchaseOrder(Long id, ReceivingOrderCreateDto updateDto) {

        try {
            ReceivingOrder receivingOrder = receivingOrderRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("해당 를 찾을 수 없습니다."));

            if(updateDto.getClientId() != null){
                Client client = clientRepository.findById(updateDto.getClientId())
                        .orElseThrow(() -> new RuntimeException("해당 거래처 정보를 찾을 수 없습니다."));
                receivingOrder.setClient(client);
            }

            if (updateDto.getManagerId() != null) {
                Employee manager = employeeRepository.findById(updateDto.getManagerId())
                        .orElseThrow(() -> new RuntimeException("해당 담당자 정보를 찾을 수 없습니다."));
                receivingOrder.setManager(manager);
            }

            if (updateDto.getWarehouseId() != null) {
                Warehouse warehouse = warehouseRepository.findById(updateDto.getWarehouseId())
                        .orElseThrow(() -> new RuntimeException("해당 창고 정보를 찾을 수 없습니다."));
                receivingOrder.setReceivingWarehouse(warehouse);
            }

            // 입고지시 일자, 납기일자 수정
            receivingOrder.setDate(updateDto.getDate() != null ? updateDto.getDate() : receivingOrder.getDate());
            receivingOrder.setDeliveryDate(updateDto.getDeliveryDate() != null ? updateDto.getDeliveryDate() : receivingOrder.getDeliveryDate());

            receivingOrder.getReceivingOrderDetails().clear();

            // 입고지시서 상세 정보 업데이트 - 등록 관련 메서드의 getReceivingOrder 메서드 사용
            ReceivingOrder newOrder = getReceivingOrder(updateDto, receivingOrder);

            ReceivingOrder updateOrder = receivingOrderRepository.save(newOrder);

            return toDetailDto(updateOrder);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 요청입니다.: " + e.getMessage());
        } catch (RuntimeException e) {
            log.error("입고지시서 수정 중 오류 발생: ", e);
            throw new RuntimeException("입고지시서 수정 중 오류가 발생했습니다.");
        }
    }

    @Override
    public String deleteReceivingOrder(Long id) {
        try{
            ReceivingOrder receivingOrder = receivingOrderRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("해당 발주 요청을 찾을 수 없습니다."));
            receivingOrderRepository.delete(receivingOrder);
            return "입고지시서가 삭제되었습니다.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        } catch (RuntimeException e) {
            return "입고지시서 삭제 중 오류가 발생했습니다.";
        }
    }

}
