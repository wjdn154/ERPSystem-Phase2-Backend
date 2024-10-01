package com.megazone.ERPSystem_phase2_Backend.logistics.service.purchase_management.purchase_order;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.Client;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.client.ClientRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.PurchaseOrder;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.PurchaseOrderDetail;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.PurchaseRequest;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.PurchaseRequestDetail;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseOrderCreateDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseOrderResponseDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseOrderResponseDetailDto.PurchaseOrderItemDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseOrderResponseDetailDto.PurchaseOrderItemDetailDto.ClientDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseOrderResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management.CurrencyRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management.purchase_order.PurchaseOrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PurchaseOrderServiceImpl implements PurchaseOrderService{

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final WarehouseRepository warehouseRepository;
    private final CurrencyRepository currencyRepository;
    private final ProductRepository productRepository;

    /**
     * 발주서 목록 조회
     * @return
     */
    @Override
    public List<PurchaseOrderResponseDto> findAllPurchaseOrders() {

        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();

        if (purchaseOrders.isEmpty()) {
            return new ArrayList<>();
        }

        return purchaseOrders.stream()
                .map(this::toListDto)
                .toList();
    }


    /** 발주서 목록 조회 관련 메서드 **/
    // Entity -> 발주서 목록 조회용 DTO 변환 메소드
    private PurchaseOrderResponseDto toListDto(PurchaseOrder purchaseOrder) {
        return PurchaseOrderResponseDto.builder()
                .id(purchaseOrder.getId())
                .clientName(purchaseOrder.getClient().getPrintClientName())  // 첫 번째 품목의 거래처 이름
                .managerName(purchaseOrder.getManager().getLastName() + purchaseOrder.getManager().getFirstName())
                .productName(getProductNameWithCount(purchaseOrder))  // 첫 번째 품목 이름과 "외 몇건" 추가
                .date(purchaseOrder.getDate())
                .deliveryDate(purchaseOrder.getDeliveryDate())
                .totalQuantity(getTotalQuantity(purchaseOrder))  // 총 수량
                .totalPrice(getTotalPrice(purchaseOrder))  // 총 가격
                .status(purchaseOrder.getStatus().toString())  // 진행 상태
                .build();
    }

    // 총 금액 계산
    private Double getTotalPrice(PurchaseOrder purchaseOrder) {
        return purchaseOrder.getPurchaseOrderDetails().stream()
                .mapToDouble(PurchaseOrderDetail::getSupplyPrice)
                .sum();
    }

    // 총 수량 계산
    private Integer getTotalQuantity(PurchaseOrder purchaseOrder) {
        return purchaseOrder.getPurchaseOrderDetails().stream()
                .mapToInt(PurchaseOrderDetail::getQuantity)
                .sum();
    }

    // 첫 번째 품목 이름과 외 몇건 처리
    private String getProductNameWithCount(PurchaseOrder purchaseOrder) {
        List<PurchaseOrderDetail> details = purchaseOrder.getPurchaseOrderDetails();

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
     * 발주서 상세 정보 조회
     * @param id
     * @return
     */
    @Override
    public Optional<PurchaseOrderResponseDetailDto> findPurchaseOrderDetailById(Long id) {
        
        return purchaseOrderRepository.findById(id)
                .map(this::toDetailDto);
    }

    /** 발주서 상세 정보 조회 관련 메서드 **/
    // Entity -> 상세 조회용 DTO 변환 메소드
    private PurchaseOrderResponseDetailDto toDetailDto(PurchaseOrder purchaseOrder) {
        return PurchaseOrderResponseDetailDto.builder()
                .id(purchaseOrder.getId())
                .date(purchaseOrder.getDate())
                .deliveryDate(purchaseOrder.getDeliveryDate())
                .status(purchaseOrder.getStatus().toString())
                .managerName(purchaseOrder.getManager().getLastName() + purchaseOrder.getManager().getFirstName())
                .warehouseName(purchaseOrder.getReceivingWarehouse().getName())
                .vatType(purchaseOrder.getVatType())
                .currency(purchaseOrder.getCurrency().getName())
                .remarks(purchaseOrder.getRemarks())
                .purchaseOrderDetails(toItemDetailDtoList(purchaseOrder.getPurchaseOrderDetails()))
                .build();
    }

    // PurchaseOrderDetail 리스트 -> PurchaseOrderItemDetailDto 리스트 변환 메서드
    private List<PurchaseOrderItemDetailDto> toItemDetailDtoList(List<PurchaseOrderDetail> details) {
        return details.stream()
                .map(this::toItemDetailDto)
                .collect(Collectors.toList());
    }

    // PurchaseOrderDetail 엔티티 -> PurchaseOrderItemDetailDto 변환 메서드
    private PurchaseOrderItemDetailDto toItemDetailDto(PurchaseOrderDetail detail) {
        Product product = detail.getProduct();
        return PurchaseOrderItemDetailDto.builder()
                .productName(product.getName())
                .productCode(product.getCode())
                .quantity(detail.getQuantity())
                .price(detail.getPrice())
                .supplyPrice(detail.getSupplyPrice())
                .vat(detail.getVat())
                .remarks(detail.getRemarks())
                .client(toClientDto(product.getClient()))
                .build();
    }

    // Client 엔티티 -> ClientDetailDto 변환 메서드
    private ClientDetailDto toClientDto(Client client) {
        return ClientDetailDto.builder()
                .clientId(client.getId())
                .clientCode(client.getCode())
                .clientName(client.getPrintClientName())
                .build();
    }

    /**
     * 발주서 직접 등록
     * @param createDto
     * @return
     */
    @Override
    public PurchaseOrderResponseDetailDto createPurchaseOrder(PurchaseOrderCreateDto createDto) {
        try {
            PurchaseOrder purchaseOrder = toEntity(createDto);
            purchaseOrder = purchaseOrderRepository.save(purchaseOrder);
            return toDetailDto(purchaseOrder);
        } catch (Exception e) {
            log.error("발주서 생성 실패: ", e);
            return null;
        }
    }

    /** 발주서 직접 등록 관련 메서드 **/
    // 발주서 등록 DTO -> Entity 변환 메소드
    private PurchaseOrder toEntity(PurchaseOrderCreateDto dto) {
        PurchaseOrder newOrder = PurchaseOrder.builder()
                .client(clientRepository.findById(dto.getClientId())
                        .orElseThrow(() -> new RuntimeException("거래처 정보를 찾을 수 없습니다.")))
                .manager(employeeRepository.findById(dto.getManagerId())
                        .orElseThrow(() -> new RuntimeException("담당자 정보를 찾을 수 없습니다.")))
                .receivingWarehouse(warehouseRepository.findById(dto.getWarehouseId())
                        .orElseThrow(() -> new RuntimeException("창고 정보를 찾을 수 없습니다.")))
                .currency(currencyRepository.findById(dto.getCurrencyId())
                        .orElseThrow(() -> new RuntimeException("통화 정보를 찾을 수 없습니다.")))
                .date(dto.getDate())
                .deliveryDate(dto.getDeliveryDate())
                .vatType(dto.getVatType())
                .remarks(dto.getRemarks())
                .build();

        return getPurchaseOrder(dto, newOrder);
    }

    private PurchaseOrder getPurchaseOrder(PurchaseOrderCreateDto dto, PurchaseOrder newOrder) {
        dto.getItems().forEach(item -> {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("품목 정보를 찾을 수 없습니다."));

            // 단가 설정
            // 품목에서 기본 단가 가져오기
            double price = product.getPurchasePrice();

            // 프론트에서 단가를 변경하지 않으면 품목에 저장된 기본 단가를 적용
            double appliedPrice = item.getPrice() != null ? item.getPrice() : price;

            // 공급가액 계산 (수량 * 제품 단가)
            double supplyPrice = item.getQuantity() * appliedPrice;

            // 부가세 계산 (내화인 경우만 10% 적용)
            Double vat = null;  // 외화의 경우 부가세 계산 안 함
            if (newOrder.getCurrency().getCode().equals("KRW") && newOrder.getVatType().equals(true)) {
                vat = supplyPrice * 0.1;  // 내화인 경우 부가세 적용되어있으면 10%
            }

            // 백엔드에서 기본 환율 가져오기
            double exchangeRate = newOrder.getCurrency().getExchangeRate();

            // 프론트에서 환율이 변경되지 않으면 DB에 저장된 기본 환율을 사용
            double appliedRate = dto.getExchangeRate() != null ? dto.getExchangeRate() : exchangeRate;

            // 원화 금액 계산
            double localAmount = supplyPrice * appliedRate;

            // 발주서 상세 항목 생성
            PurchaseOrderDetail detail = PurchaseOrderDetail.builder()
                    .product(product)
                    .quantity(item.getQuantity())
                    .price(appliedPrice)
                    .supplyPrice(supplyPrice)
                    .localAmount(localAmount)
                    .vat(vat)
                    .remarks(item.getRemarks())
                    .build();

            newOrder.addPurchaseOrderDetail(detail);
        });
        return newOrder;
    }


    /**
     * 발주서 수정
     * @param id
     * @param updateDto
     * @return
     */
    @Override
    public PurchaseOrderResponseDetailDto updatePurchaseOrder(Long id, PurchaseOrderCreateDto updateDto) {
        try {
            PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("해당 발주 요청 정보를 찾을 수 없습니다."));

            if(updateDto.getClientId() != null){
                Client client = clientRepository.findById(updateDto.getClientId())
                        .orElseThrow(() -> new RuntimeException("해당 거래처 정보를 찾을 수 없습니다."));
            }
            
            if (updateDto.getManagerId() != null) {
                Employee manager = employeeRepository.findById(updateDto.getManagerId())
                        .orElseThrow(() -> new RuntimeException("해당 담당자 정보를 찾을 수 없습니다."));
                purchaseOrder.setManager(manager);
            }

            if (updateDto.getWarehouseId() != null) {
                Warehouse warehouse = warehouseRepository.findById(updateDto.getWarehouseId())
                        .orElseThrow(() -> new RuntimeException("해당 창고 정보를 찾을 수 없습니다."));
                purchaseOrder.setReceivingWarehouse(warehouse);
            }

            // 발주요청 일자, 납기일자 수정
            purchaseOrder.setDate(updateDto.getDate() != null ? updateDto.getDate() : purchaseOrder.getDate());
            purchaseOrder.setDeliveryDate(updateDto.getDeliveryDate() != null ? updateDto.getDeliveryDate() : purchaseOrder.getDeliveryDate());

            // 통화 수정
            if (updateDto.getCurrencyId() != null) {
                purchaseOrder.setCurrency(currencyRepository.findById(updateDto.getCurrencyId())
                        .orElseThrow(() -> new RuntimeException("통화 정보를 찾을 수 없습니다.")));
                purchaseOrder.setCurrency(purchaseOrder.getCurrency());

            }

            // 부가세 적용 수정
            purchaseOrder.setVatType(updateDto.getVatType() != null ? updateDto.getVatType() : purchaseOrder.getVatType());

            purchaseOrder.getPurchaseOrderDetails().clear();  // 기존 항목을 제거

            // 발주 상세 정보 업데이트 - 등록 관련 메서드의 getPurchaseRequest 메서드 사용
            PurchaseOrder newOrder = getPurchaseOrder(updateDto, purchaseOrder);

            PurchaseOrder updateOrder = purchaseOrderRepository.save(newOrder);

            return toDetailDto(updateOrder);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 요청입니다.: " + e.getMessage());
        } catch (RuntimeException e) {
            log.error("발주서 수정 중 오류 발생: ", e);
            throw new RuntimeException("발주서 수정 중 오류가 발생했습니다.");
        }
    }

    /**
     * 발주서 삭제
     * @param id
     * @return
     */
    @Override
    public String deletePurchaseOrder(Long id) {
        try{
            PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("해당 발주 요청을 찾을 수 없습니다."));
            purchaseOrderRepository.delete(purchaseOrder);
            return "발주서가 삭제되었습니다.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        } catch (RuntimeException e) {
            return "발주서 삭제 중 오류가 발생했습니다.";
        }
    }
}
