package com.megazone.ERPSystem_phase2_Backend.logistics.service.sales_management.orders;


import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatAmountWithSupplyAmountDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.client.ClientRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.sales_and_purchase_voucher_entry.VatTypeService;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.SearchDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.Orders;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.OrdersDetail;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.orders.OrdersCreateDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.orders.OrdersResponseDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.orders.OrdersResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management.CurrencyRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.sales_management.orders.OrdersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository ordersRepository;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final WarehouseRepository warehouseRepository;
    private final CurrencyRepository currencyRepository;
    private final ProductRepository productRepository;
    private final VatTypeService vatTypeService;



    @Override
    public List<OrdersResponseDto> findAllOrders(SearchDTO dto) {
        List<Orders> orders;

        // dto가 null이거나 조건이 모두 null일 경우 모든 발주서 조회
        if (dto == null || (dto.getStartDate() == null && dto.getEndDate() == null && dto.getClientId() == null && dto.getState() == null)) {
            orders = ordersRepository.findAll(); // 전체 발주서 조회
        } else {
            // 조건이 있는 경우 QueryDSL을 사용하여 검색
            orders = ordersRepository.findBySearch(dto);
        }

        // 발주서가 없는 경우 빈 리스트 반환
        return orders.isEmpty()
                ? Collections.emptyList()
                : orders.stream()
                .map(this::toListDto)
                .toList();
    }

    /** 견적서 목록 조회 관련 메서드 **/
    // Entity -> 견적서 목록 조회용 DTO 변환 메소드
    private OrdersResponseDto toListDto(Orders orders) {

        return OrdersResponseDto.builder()
                .id(orders.getId())
                .clientName(orders.getClient().getPrintClientName())
                .date(orders.getDate())
                .deliveryDate(orders.getDeliveryDate())
                .productName(getProductNameWithCount(orders))
                .warehouseName(orders.getShippingWarehouse().getName())
                .vatName(vatTypeService.vatTypeGet(orders.getVatId()).getVatTypeName())
                .totalPrice(getTotalPrice(orders))
                .totalQuantity(getTotalQuantity(orders))
                .status(orders.getState().toString())
                .build();
    }

    private BigDecimal getTotalPrice(Orders orders) {
        return orders.getOrdersDetails().stream()
                .map(OrdersDetail::getSupplyPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Integer getTotalQuantity(Orders orders) {
        return orders.getOrdersDetails().stream()
                .map(OrdersDetail::getQuantity)
                .reduce(0, Integer::sum);
    }

    private String getProductNameWithCount(Orders orders) {
        List<OrdersDetail> details = orders.getOrdersDetails();

        if (details.isEmpty()) {
            return "품목 없음";  // 품목이 없을 경우 처리
        }

        String firstProductName = details.get(0).getProduct().getName();

        // 품목이 2개 이상일 때 "외 N건"을 붙임
        if (details.size() > 1) {
            return firstProductName + " 외 " + (details.size() - 1) + "건";
        } else {
            return firstProductName;  // 품목이 하나일 경우 그냥 첫 번째 품목 이름만 반환
        }
    }


    /**
     * 견적서 상세 정보 조회
     * @param id
     * @return
     */
    @Override
    public Optional<OrdersResponseDetailDto> findOrdersDetailById(Long id) {
        return ordersRepository.findById(id)
                .map(this::toDetailDto);
    }

    /** 견적서 상세 정보 조회 관련 메서드 **/
    // Entity -> 상세 조회용 DTO 변환 메소드
    private OrdersResponseDetailDto toDetailDto(Orders orders) {
        return OrdersResponseDetailDto.builder()
                .id(orders.getId())
                .date(orders.getDate())
                .deliveryDate(orders.getDeliveryDate())
                .clientId(orders.getClient().getId())
                .clientCode(orders.getClient().getCode())
                .clientName(orders.getClient().getPrintClientName())
                .managerId(orders.getManager().getId())
                .managerCode(orders.getManager().getEmployeeNumber())
                .managerName(orders.getManager().getLastName() + orders.getManager().getFirstName())
                .warehouseId(orders.getShippingWarehouse().getId())
                .warehouseCode(orders.getShippingWarehouse().getCode())
                .warehouseName(orders.getShippingWarehouse().getName())
                .exchangeRate(orders.getCurrency().getExchangeRate())
                .vatId(orders.getVatId())
                .vatCode(vatTypeService.vatTypeGet(orders.getVatId()).getVatTypeCode())
                .vatName(vatTypeService.vatTypeGet(orders.getVatId()).getVatTypeName())
                .journalEntryCode(orders.getJournalEntryCode())
                .electronicTaxInvoiceStatus(orders.getElectronicTaxInvoiceStatus().toString())
                .currencyId(orders.getCurrency().getId())
                .currency(orders.getCurrency().getName())
                .remarks(orders.getRemarks())
                .status(orders.getState().toString())
                .ordersDetails(toItemDetailDtoList(orders.getOrdersDetails()))
                .build();
    }

    private List<OrdersResponseDetailDto.OrdersItemDetailDto> toItemDetailDtoList(List<OrdersDetail> details) {
        return details.stream()
                .map(this::toItemDetailDto)
                .collect(Collectors.toList());
    }

    private OrdersResponseDetailDto.OrdersItemDetailDto toItemDetailDto(OrdersDetail detail) {
        Product product = detail.getProduct();
        return OrdersResponseDetailDto.OrdersItemDetailDto.builder()
                .productId(product.getId())
                .productCode(product.getCode())
                .productName(product.getName())
                .standard(product.getStandard())
                .price(product.getSalesPrice())
                .quantity(detail.getQuantity())
                .supplyPrice(detail.getSupplyPrice())
                .localAmount(detail.getLocalAmount())
                .vat(detail.getVat())
                .remarks(detail.getRemarks())
                .build();
    }

    /**
     * 견적서 등록
     * @param createDto
     * @return
     */
    @Override
    public OrdersResponseDetailDto createOrders(OrdersCreateDto createDto) {
        try {
            Orders orders = toEntity(createDto);
            orders = ordersRepository.save(orders);
            return toDetailDto(orders);
        } catch (Exception e) {
            log.error("견적서 생성 실패: ", e);
            return null;
        }
    }

    /** 견적서 등록 관련 메서드 **/
    // 견적서 등록 DTO -> Entity 변환 메소드
    private Orders toEntity(OrdersCreateDto dto) {
        Orders orders = Orders.builder()
                .client(clientRepository.findById(dto.getClientId())
                        .orElseThrow(() -> new RuntimeException("거래처 정보를 찾을 수 없습니다.")))
                .manager(employeeRepository.findById(dto.getManagerId())
                        .orElseThrow(() -> new RuntimeException("담당자 정보를 찾을 수 없습니다.")))
                .shippingWarehouse(warehouseRepository.findById(dto.getWarehouseId())
                        .orElseThrow(() -> new RuntimeException("창고 정보를 찾을 수 없습니다.")))
                .currency(currencyRepository.findById(dto.getCurrencyId())
                        .orElseThrow(() -> new RuntimeException("통화 정보를 찾을 수 없습니다.")))
                .vatId(dto.getVatId())
                .date(dto.getDate())
                .deliveryDate(dto.getDeliveryDate())
                .journalEntryCode(dto.getJournalEntryCode())
                .remarks(dto.getRemarks())
                .build();

        return getOrders(dto, orders);
    }

    private Orders getOrders(OrdersCreateDto dto, Orders orders) {
        if (orders.getCurrency() == null) {
            throw new RuntimeException("통화 정보가 설정되지 않았습니다.");
        }

        dto.getItems().forEach(item -> {
            if (item.getProductId() == null) {
                throw new IllegalArgumentException("품목 ID가 제공되지 않았습니다.");
            }

            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("품목 정보를 찾을 수 없습니다. ID: " + item.getProductId()));

            BigDecimal supplyPrice = BigDecimal.valueOf(item.getQuantity()).multiply(product.getSalesPrice());

            VatAmountWithSupplyAmountDTO vatAmountWithSupplyAmountDTO = new VatAmountWithSupplyAmountDTO();
            vatAmountWithSupplyAmountDTO.setSupplyAmount(supplyPrice);

            BigDecimal localAmount = null;
            BigDecimal vat = null;

            if ("KRW".equals(orders.getCurrency().getCode())) {
                vat = vatTypeService.vatAmountCalculate(vatAmountWithSupplyAmountDTO);
            } else if (orders.getCurrency().getExchangeRate() != null) {
                localAmount = supplyPrice.multiply(orders.getCurrency().getExchangeRate());
            } else {
                throw new RuntimeException("환율 정보가 없습니다.");
            }

            OrdersDetail detail = OrdersDetail.builder()
                    .product(product)
                    .quantity(item.getQuantity())
                    .supplyPrice(supplyPrice)
                    .localAmount(localAmount)
                    .vat(vat)
                    .remarks(item.getRemarks())
                    .build();
            orders.addOrdersDetail(detail);
        });
        return orders;
    }

    @Override
    public OrdersResponseDetailDto updateOrders(Long id, OrdersCreateDto updateDto) {
        try {
            Orders orders = ordersRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("해당 견적서 정보를 찾을 수 없습니다."));

            if (updateDto.getManagerId() != null) {
                Employee manager = employeeRepository.findById(updateDto.getManagerId())
                        .orElseThrow(() -> new RuntimeException("해당 담당자 정보를 찾을 수 없습니다."));
                orders.setManager(manager);
            }

            if (updateDto.getWarehouseId() != null) {
                Warehouse warehouse = warehouseRepository.findById(updateDto.getWarehouseId())
                        .orElseThrow(() -> new RuntimeException("해당 창고 정보를 찾을 수 없습니다."));
                orders.setShippingWarehouse(warehouse);
            }

            // 발주요청 일자, 납기일자 수정
            orders.setDate(updateDto.getDate() != null ? updateDto.getDate() : orders.getDate());

            // 통화 수정
            if (updateDto.getCurrencyId() != null) {
                orders.setCurrency(currencyRepository.findById(updateDto.getCurrencyId())
                        .orElseThrow(() -> new RuntimeException("통화 정보를 찾을 수 없습니다.")));
            }

            // 부가세 적용 수정
            orders.setVatId(updateDto.getVatId() != null ? updateDto.getVatId() : orders.getVatId());
            orders.setRemarks(updateDto.getRemarks() != null ? updateDto.getRemarks() : orders.getRemarks());

            orders.getOrdersDetails().clear();  // 기존 항목을 제거

            // 발주 상세 정보 업데이트 - 등록 관련 메서드의 getOrdersRequest 메서드 사용
            Orders newOrders = getOrders(updateDto, orders);

            Orders updatedOrders = ordersRepository.save(newOrders);

            return toDetailDto(updatedOrders);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 요청입니다.: " + e.getMessage());
        } catch (RuntimeException e) {
            log.error("견적서 수정 중 오류 발생: ", e);
            throw new RuntimeException("견적서 수정 중 오류가 발생했습니다.");
        }
    }

    @Override
    public String deleteOrders(Long id) {
        try{
            Orders orders = ordersRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("해당 구매서를 찾을 수 없습니다."));
            ordersRepository.delete(orders);
            return "구매서가 삭제되었습니다.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        } catch (RuntimeException e) {
            return "구매서 삭제 중 오류가 발생했습니다.";
        }
    }

}
