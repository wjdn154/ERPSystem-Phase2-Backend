package com.megazone.ERPSystem_phase2_Backend.logistics.service.sales_management.shipping_order;


import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.Client;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.client.ClientRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.SearchDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.ShippingOrder;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.ShippingOrderDetail;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.shippingOrder.ShippingOrderCreateDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.shippingOrder.ShippingOrderResponseDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.shippingOrder.ShippingOrderResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management.CurrencyRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.sales_management.shipping_order.ShippingOrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.shippingOrder.ShippingOrderResponseDetailDto.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ShippingOrderServiceImpl implements ShippingOrderService {

    private final ShippingOrderRepository shippingOrderRepository;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final WarehouseRepository warehouseRepository;
    private final CurrencyRepository currencyRepository;
    private final ProductRepository productRepository;


    /**
     * 출하지시서 목록 조회
     * @return
     */
    @Override
    public List<ShippingOrderResponseDto> findAllShippingOrders(SearchDTO dto) {

        List<ShippingOrder> shippingOrders;

        // dto가 null이거나 조건이 모두 null일 경우 모든 발주서 조회
        if (dto == null || (dto.getStartDate() == null && dto.getEndDate() == null && dto.getClientId() == null && dto.getState() == null)) {
            shippingOrders = shippingOrderRepository.findAll(); // 전체 발주서 조회
        } else {
            // 조건이 있는 경우 QueryDSL을 사용하여 검색
            shippingOrders = shippingOrderRepository.findBySearch(dto);
        }

        // 발주서가 없는 경우 빈 리스트 반환
        return shippingOrders.isEmpty()
                ? Collections.emptyList()
                : shippingOrders.stream()
                .map(this::toListDto)
                .toList();
    }

    /** 출하지시서 목록 조회 관련 메서드 **/
    // Entity -> 출하지시서 목록 조회용 DTO 변환 메소드
    private ShippingOrderResponseDto toListDto(ShippingOrder shippingOrder) {
        return ShippingOrderResponseDto.builder()
                .id(shippingOrder.getId())
                .clientName(shippingOrder.getClient().getPrintClientName())
                .managerName(shippingOrder.getManager().getLastName() + shippingOrder.getManager().getFirstName())
                .warehouseName(shippingOrder.getShippingWarehouse().getName())
                .productName(getProductNameWithCount(shippingOrder))
                .totalQuantity(getTotalQuantity(shippingOrder))
                .date(shippingOrder.getDate())
                .shippingDate(shippingOrder.getShippingDate())
                .status(shippingOrder.getState().toString())
                .remarks(shippingOrder.getRemarks())
                .build();
    }

    // 총 수량 계산
    private Integer getTotalQuantity(ShippingOrder shippingOrder) {
        return shippingOrder.getShippingOrderDetails().stream()
                .mapToInt(ShippingOrderDetail::getQuantity)
                .sum();
    }

    // 첫 번째 품목 이름과 외 몇건 처리
    private String getProductNameWithCount(ShippingOrder shippingOrder) {
        List<ShippingOrderDetail> details = shippingOrder.getShippingOrderDetails();

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
     * 출하지시서 상세 정보 조회
     * @param id
     * @return
     */
    @Override
    public Optional<ShippingOrderResponseDetailDto> findShippingOrderDetailById(Long id) {

        return shippingOrderRepository.findById(id)
                .map(this::toDetailDto);
    }


    /** 출하지시서 상세 정보 조회 관련 메서드 **/
    // Entity -> 상세 조회용 DTO 변환 메소드
    private ShippingOrderResponseDetailDto toDetailDto(ShippingOrder shippingOrder) {
        return builder()
                .id(shippingOrder.getId())
                .clientName(shippingOrder.getClient().getPrintClientName())
                .clientCode(shippingOrder.getClient().getCode())
                .managerCode(shippingOrder.getManager().getEmployeeNumber())
                .managerName(shippingOrder.getManager().getLastName() + shippingOrder.getManager().getFirstName())
                .managerContact(shippingOrder.getManager().getPhoneNumber())
                .warehouseCode(shippingOrder.getShippingWarehouse().getCode())
                .warehouseName(shippingOrder.getShippingWarehouse().getName())
                .warehouseAddress(shippingOrder.getShippingAddress())
                .postalCode(shippingOrder.getPostalCode())
                .date(shippingOrder.getDate())
                .shippingDate(shippingOrder.getShippingDate())
                .remarks(shippingOrder.getRemarks())
                .status(shippingOrder.getState().toString())
                .shippingOrderDetails(toItemDetailDtoList(shippingOrder.getShippingOrderDetails()))
                .build();
    }

    private List<ShippingOrderItemDetailDto> toItemDetailDtoList(List<ShippingOrderDetail> details) {
        return details.stream()
                .map(this::toItemDetailDto)
                .collect(Collectors.toList());
    }

    private ShippingOrderItemDetailDto toItemDetailDto(ShippingOrderDetail detail) {
        Product product = detail.getProduct();
        return ShippingOrderItemDetailDto.builder()
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
    public ShippingOrderResponseDetailDto createShippingOrder(ShippingOrderCreateDto createDto) {
        try {
            ShippingOrder shippingOrder = toEntity(createDto);
            shippingOrder = shippingOrderRepository.save(shippingOrder);
            return toDetailDto(shippingOrder);
        } catch (Exception e) {
            log.error("출하지시서 생성 실패: ", e);
            return null;
        }
    }

    /** 출하지시서 직접 등록 관련 메서드 **/
    // 출하지시서 등록 DTO -> Entity 변환 메소드
    private ShippingOrder toEntity(ShippingOrderCreateDto dto) {
        ShippingOrder newOrder = ShippingOrder.builder()
                .client(clientRepository.findById(dto.getClientId())
                        .orElseThrow(() -> new RuntimeException("거래처 정보를 찾을 수 없습니다.")))
                .manager(employeeRepository.findById(dto.getManagerId())
                        .orElseThrow(() -> new RuntimeException("담당자 정보를 찾을 수 없습니다.")))
                .shippingWarehouse(warehouseRepository.findById(dto.getWarehouseId())
                        .orElseThrow(() -> new RuntimeException("창고 정보를 찾을 수 없습니다.")))
                .date(dto.getDate())
                .shippingDate(dto.getShippingDate())
                .remarks(dto.getRemarks())
                .build();

        return getShippingOrder(dto, newOrder);
    }

    private ShippingOrder getShippingOrder(ShippingOrderCreateDto dto, ShippingOrder newOrder) {
        dto.getItems().forEach(item -> {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("품목 정보를 찾을 수 없습니다."));


            ShippingOrderDetail detail = ShippingOrderDetail.builder()
                    .product(product)
                    .quantity(item.getQuantity())
                    .remarks(item.getRemarks())
                    .build();
            newOrder.addShippingOrderDetail(detail);
        });

        return newOrder;
    }

    /**
     * 출하지시서 수정
     * @param id
     * @param updateDto
     * @return
     */
    @Override
    public ShippingOrderResponseDetailDto updateShippingOrder(Long id, ShippingOrderCreateDto updateDto) {

        try {
            ShippingOrder shippingOrder = shippingOrderRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("해당 를 찾을 수 없습니다."));

            if(updateDto.getClientId() != null){
                Client client = clientRepository.findById(updateDto.getClientId())
                        .orElseThrow(() -> new RuntimeException("해당 거래처 정보를 찾을 수 없습니다."));
                shippingOrder.setClient(client);
            }

            if (updateDto.getManagerId() != null) {
                Employee manager = employeeRepository.findById(updateDto.getManagerId())
                        .orElseThrow(() -> new RuntimeException("해당 담당자 정보를 찾을 수 없습니다."));
                shippingOrder.setManager(manager);
            }

            if (updateDto.getWarehouseId() != null) {
                Warehouse warehouse = warehouseRepository.findById(updateDto.getWarehouseId())
                        .orElseThrow(() -> new RuntimeException("해당 창고 정보를 찾을 수 없습니다."));
                shippingOrder.setShippingWarehouse(warehouse);
            }

            // 입고지시 일자, 납기일자 수정
            shippingOrder.setDate(updateDto.getDate() != null ? updateDto.getDate() : shippingOrder.getDate());
            shippingOrder.setShippingDate(updateDto.getShippingDate() != null ? updateDto.getShippingDate() : shippingOrder.getShippingDate());

            shippingOrder.setRemarks(updateDto.getRemarks());

            shippingOrder.getShippingOrderDetails().clear();

            // 출하지시서 상세 정보 업데이트 - 등록 관련 메서드의 getShippingOrder 메서드 사용
            ShippingOrder newOrder = getShippingOrder(updateDto, shippingOrder);

            ShippingOrder updateOrder = shippingOrderRepository.save(newOrder);

            return toDetailDto(updateOrder);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 요청입니다.: " + e.getMessage());
        } catch (RuntimeException e) {
            log.error("출하지시서 수정 중 오류 발생: ", e);
            throw new RuntimeException("출하지시서 수정 중 오류가 발생했습니다.");
        }
    }

    @Override
    public String deleteShippingOrder(Long id) {
        try{
            ShippingOrder shippingOrder = shippingOrderRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("해당 발주 요청을 찾을 수 없습니다."));
            shippingOrderRepository.delete(shippingOrder);
            return "출하지시서가 삭제되었습니다.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        } catch (RuntimeException e) {
            return "출하지시서 삭제 중 오류가 발생했습니다.";
        }
    }

}
