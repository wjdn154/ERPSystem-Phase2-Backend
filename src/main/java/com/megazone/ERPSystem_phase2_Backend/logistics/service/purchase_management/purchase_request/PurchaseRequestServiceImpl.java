package com.megazone.ERPSystem_phase2_Backend.logistics.service.purchase_management.purchase_request;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.Client;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.PurchaseRequest;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.PurchaseRequestDetail;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseRequestCreateDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseRequestResponseDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseRequestResponseDetailDto.PurchaseRequestItemDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseRequestResponseDetailDto.PurchaseRequestItemDetailDto.ClientDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseRequestResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management.CurrencyRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management.purchase_request.PurchaseRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PurchaseRequestServiceImpl implements PurchaseRequestService {

    private final PurchaseRequestRepository purchaseRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final WarehouseRepository warehouseRepository;
    private final CurrencyRepository currencyRepository;
    private final ProductRepository productRepository;

    /**
     * 발주요청 목록 조회
     * @return
     */
    @Override
    public List<PurchaseRequestResponseDto> findAllPurchaseRequests() {

        // 모든 발주 요청을 가져옴
        List<PurchaseRequest> purchaseRequests = purchaseRequestRepository.findAll();

        if (purchaseRequests.isEmpty()) {
            return new ArrayList<>();
        }

        return purchaseRequests.stream()
                .map(this::toListDto)  // 각 발주 요청을 Response DTO로 변환
                .toList();
    }

    /** 발주요청 목록 조회 관련 메서드 **/
    // Entity -> 발주요청 목록 조회용 DTO 변환 메소드
    private PurchaseRequestResponseDto toListDto(PurchaseRequest purchaseRequest) {
        return PurchaseRequestResponseDto.builder()
                .id(purchaseRequest.getId())
                .clientName(getClientNameFromFirstProduct(purchaseRequest))  // 첫 번째 품목의 거래처 이름
                .productName(getProductNameWithCount(purchaseRequest))  // 첫 번째 품목 이름과 "외 몇건" 추가
                .date(purchaseRequest.getDate())
                .deliveryDate(purchaseRequest.getDeliveryDate())
                .totalQuantity(getTotalQuantity(purchaseRequest))  // 총 수량
                .totalPrice(getTotalPrice(purchaseRequest))  // 총 가격
                .status(purchaseRequest.getStatus().toString())  // 진행 상태
                .build();
    }

    // 첫 번째 품목의 거래처 이름 가져오기
    private String getClientNameFromFirstProduct(PurchaseRequest purchaseRequest) {
        if (!purchaseRequest.getPurchaseRequestDetails().isEmpty()) {
            return purchaseRequest.getPurchaseRequestDetails().get(0).getProduct().getClient().getPrintClientName();
        }
        return null;
    }

    // 첫 번째 품목 이름과 외 몇건 처리
    private String getProductNameWithCount(PurchaseRequest purchaseRequest) {
        List<PurchaseRequestDetail> details = purchaseRequest.getPurchaseRequestDetails();

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

    // 총 수량 계산
    private int getTotalQuantity(PurchaseRequest purchaseRequest) {
        return purchaseRequest.getPurchaseRequestDetails().stream()
                .mapToInt(PurchaseRequestDetail::getQuantity)
                .sum();
    }

    // 총 금액 계산
    private BigDecimal getTotalPrice(PurchaseRequest purchaseRequest) {
        return purchaseRequest.getPurchaseRequestDetails().stream()
                .map(PurchaseRequestDetail::getSupplyPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 발주요청 상세 정보 조회
     * @param id
     * @return
     */
    @Override
    public Optional<PurchaseRequestResponseDetailDto> findPurchaseRequestDetailById(Long id) {

        return purchaseRequestRepository.findById(id)
                .map(this::toDetailDto);
    }

    /** 발주요청 상세 정보 조회 관련 메서드 **/
    //Entity -> 상세 조회용 DTO 변환 메소드
    public PurchaseRequestResponseDetailDto toDetailDto(PurchaseRequest purchaseRequest) {
        return PurchaseRequestResponseDetailDto.builder()
                .id(purchaseRequest.getId())
                .date(purchaseRequest.getDate())
                .deliveryDate(purchaseRequest.getDeliveryDate())
                .status(purchaseRequest.getStatus().toString())
                .managerName(purchaseRequest.getManager().getLastName() + purchaseRequest.getManager().getFirstName())  // 담당자 이름
                .warehouseName(purchaseRequest.getReceivingWarehouse().getName())  // 입고 창고 이름
                .vatType(purchaseRequest.getVatType())
                .currency(purchaseRequest.getCurrency().getName())  // 통화 종류
                .remarks(purchaseRequest.getRemarks())
                .purchaseRequestDetails(toItemDetailDtoList(purchaseRequest.getPurchaseRequestDetails()))  // 상세 목록 변환
                .build();
    }

    // PurchaseRequestDetail 리스트 -> PurchaseRequestItemDetailDto 리스트 변환 메서드
    private List<PurchaseRequestItemDetailDto> toItemDetailDtoList(List<PurchaseRequestDetail> details) {
        return details.stream()
                .map(this::toItemDetailDto)  // 각 상세 항목을 DTO로 변환
                .collect(Collectors.toList());
    }

    // PurchaseRequestDetail 엔티티 -> PurchaseRequestItemDetailDto 변환 메서드
    private PurchaseRequestItemDetailDto toItemDetailDto(PurchaseRequestDetail detail) {
        Product product = detail.getProduct();
        return PurchaseRequestItemDetailDto.builder()
                .productName(product.getName())  // 품목명
                .productCode(product.getCode())  // 품목 코드
                .quantity(detail.getQuantity())  // 수량
                .price(detail.getPrice()) // 단가
                .supplyPrice(detail.getSupplyPrice())  // 공급가액
                .vat(detail.getVat())  // 부가세
                .remarks(detail.getRemarks())  // 비고
                .client(toClientDto(product.getClient()))  // 거래처 정보 변환
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
     * 발주요청 등록
     * @param createDto
     * @return
     */
    @Override
    public PurchaseRequestResponseDetailDto createPurchaseRequest(PurchaseRequestCreateDto createDto) {
        try {
            PurchaseRequest purchaseRequest = toEntity(createDto);
            purchaseRequest = purchaseRequestRepository.save(purchaseRequest);
            return toDetailDto(purchaseRequest);
        } catch (Exception e) {
            log.error("발주 요청 생성 실패: ", e);
            return null;
        }
    }

    /** 발주요청 등록 관련 메서드 **/
    // 발주 요청 등록 DTO -> Entity 변환 메소드
    private PurchaseRequest toEntity(PurchaseRequestCreateDto dto) {

        PurchaseRequest newRequest = PurchaseRequest.builder()
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

        return getPurchaseRequest(dto, newRequest);
    }

    private PurchaseRequest getPurchaseRequest(PurchaseRequestCreateDto dto, PurchaseRequest newRequest) {
        dto.getItems().forEach(item -> {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("품목 정보를 찾을 수 없습니다."));

            // 단가 설정
            // 품목에서 기본 단가 가져오기
            BigDecimal price = product.getPurchasePrice();

            // 프론트에서 단가를 변경하지 않으면 품목에 저장된 기본 단가를 적용
            BigDecimal appliedPrice = item.getPrice() != null ? item.getPrice() : price;

            // 공급가액 계산 (수량 * 제품 단가)
            BigDecimal supplyPrice = BigDecimal.valueOf(item.getQuantity()).multiply(appliedPrice);

            // 부가세 계산 (내화인 경우만 10% 적용)
            BigDecimal vat = null;  // 외화의 경우 부가세 계산 안 함
            if (newRequest.getCurrency().getCode().equals("KRW") && newRequest.getVatType().equals(true)) {
                vat = supplyPrice.multiply(BigDecimal.valueOf(0.1));  // 내화인 경우 부가세 적용되어있으면 10%
            }

            // 백엔드에서 기본 환율 가져오기
            BigDecimal exchangeRate = newRequest.getCurrency().getExchangeRate();

            // 프론트에서 환율이 변경되지 않으면 DB에 저장된 기본 환율을 사용
            BigDecimal appliedRate = dto.getExchangeRate() != null ? dto.getExchangeRate() : exchangeRate;

            // 원화 금액 계산
            BigDecimal localAmount = supplyPrice.multiply(appliedRate);

            // 발주 요청 상세 항목 생성
            PurchaseRequestDetail detail = PurchaseRequestDetail.builder()
                    .product(product)
                    .quantity(item.getQuantity())
                    .price(appliedPrice)
                    .supplyPrice(supplyPrice)  // 공급가액 (외화 또는 내화)
                    .localAmount(localAmount)  // 원화 금액 (환율 적용)
                    .vat(vat)  // 부가세 (내화일 경우에만 적용)
                    .remarks(item.getRemarks())
                    .build();

            newRequest.addPurchaseRequestDetail(detail);
        });
        return newRequest;
    }

    /**
     * 발주요청 수정
     * @param updateDto
     * @return
     */
    @Override
    public PurchaseRequestResponseDetailDto updatePurchaseRequest(Long id, PurchaseRequestCreateDto updateDto) {
        try {
            PurchaseRequest purchaseRequest = purchaseRequestRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("해당 발주 요청 정보를 찾을 수 없습니다."));

            if (updateDto.getManagerId() != null) {
                Employee manager = employeeRepository.findById(updateDto.getManagerId())
                        .orElseThrow(() -> new RuntimeException("해당 담당자 정보를 찾을 수 없습니다."));
                purchaseRequest.setManager(manager);
            }

            if (updateDto.getWarehouseId() != null) {
                Warehouse warehouse = warehouseRepository.findById(updateDto.getWarehouseId())
                        .orElseThrow(() -> new RuntimeException("해당 창고 정보를 찾을 수 없습니다."));
                purchaseRequest.setReceivingWarehouse(warehouse);
            }

            // 발주요청 일자, 납기일자 수정
            purchaseRequest.setDate(updateDto.getDate() != null ? updateDto.getDate() : purchaseRequest.getDate());
            purchaseRequest.setDeliveryDate(updateDto.getDeliveryDate() != null ? updateDto.getDeliveryDate() : purchaseRequest.getDeliveryDate());

            // 통화 수정
            if (updateDto.getCurrencyId() != null) {
                purchaseRequest.setCurrency(currencyRepository.findById(updateDto.getCurrencyId())
                        .orElseThrow(() -> new RuntimeException("통화 정보를 찾을 수 없습니다.")));
            }

            // 부가세 적용 수정
            purchaseRequest.setVatType(updateDto.getVatType() != null ? updateDto.getVatType() : purchaseRequest.getVatType());

            purchaseRequest.getPurchaseRequestDetails().clear();  // 기존 항목을 제거

            // 발주 상세 정보 업데이트 - 등록 관련 메서드의 getPurchaseRequest 메서드 사용
            PurchaseRequest newRequest = getPurchaseRequest(updateDto, purchaseRequest);

            PurchaseRequest updateRequest = purchaseRequestRepository.save(newRequest);

            return toDetailDto(updateRequest);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 요청입니다.: " + e.getMessage());
        } catch (RuntimeException e) {
            log.error("발주 요청 수정 중 오류 발생: ", e);
            throw new RuntimeException("발주 요청 수정 중 오류가 발생했습니다.");
        }
    }

    /**
     * 발주 요청 삭제
     * @param id
     * @return
     */
    @Override
    public String deletePurchaseRequest(Long id) {

        try{
            PurchaseRequest purchaseRequest = purchaseRequestRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("해당 발주 요청을 찾을 수 없습니다."));
            purchaseRequestRepository.delete(purchaseRequest);
            return "발주 요청이 삭제되었습니다.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        } catch (RuntimeException e) {
            return "발주 요청 삭제 중 오류가 발생했습니다.";
        }
    }


}