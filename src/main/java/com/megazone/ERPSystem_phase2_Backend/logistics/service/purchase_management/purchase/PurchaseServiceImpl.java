package com.megazone.ERPSystem_phase2_Backend.logistics.service.purchase_management.purchase;

import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.JournalEntry;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatAmountWithSupplyAmountDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.client.ClientRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.sales_and_purchase_voucher_entry.VatTypeService;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.*;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseCreateDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseResponseDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseResponseDetailDto.PurchaseItemDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.PurchaseResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.SearchDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management.CurrencyRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management.purchase.PurchaseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final WarehouseRepository warehouseRepository;
    private final CurrencyRepository currencyRepository;
    private final ProductRepository productRepository;
    private final VatTypeService vatTypeService;

    /**
     * 구매서 목록 조회
     * @return
     */
    @Override
    public List<PurchaseResponseDto> findAllPurchases(SearchDTO dto) {

        List<Purchase> purchases;

        // dto가 null이거나 조건이 모두 null일 경우 모든 발주서 조회
        if (dto == null || (dto.getStartDate() == null && dto.getEndDate() == null && dto.getClientId() == null && dto.getState() == null)) {
            purchases = purchaseRepository.findAll(); // 전체 발주서 조회
        } else {
            // 조건이 있는 경우 QueryDSL을 사용하여 검색
            purchases = purchaseRepository.findBySearch(dto);
        }

        // 발주서가 없는 경우 빈 리스트 반환
        return purchases.isEmpty()
                ? Collections.emptyList()
                : purchases.stream()
                .map(this::toListDto)
                .toList();
    }

    /** 구매서 목록 조회 관련 메서드 **/
    // Entity -> 구매서 목록 조회용 DTO 변환 메소드
    private PurchaseResponseDto toListDto(Purchase purchase) {

        return PurchaseResponseDto.builder()
                .id(purchase.getId())
                .clientName(purchase.getClient().getPrintClientName())
                .date(purchase.getDate())
                .productName(getProductNameWithCount(purchase))
                .warehouseName(purchase.getReceivingWarehouse().getName())
                .vatName(vatTypeService.vatTypeGet(purchase.getVatId()).getVatTypeName())
                .totalPrice(getTotalPrice(purchase))
                .totalQuantity(getTotalQuantity(purchase))
                .status(purchase.getStatus().toString())
                .accountingReflection(purchase.getAccountingReflection())
                .build();
    }

    private BigDecimal getTotalPrice(Purchase purchase) {
        return purchase.getPurchaseDetails().stream()
                .map(PurchaseDetail::getSupplyPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Integer getTotalQuantity(Purchase purchase) {
        return purchase.getPurchaseDetails().stream()
                .map(PurchaseDetail::getQuantity)
                .reduce(0, Integer::sum);
    }

    private String getProductNameWithCount(Purchase purchase) {
        List<PurchaseDetail> details = purchase.getPurchaseDetails();

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
     * 구매서 상세 정보 조회
     * @param id
     * @return
     */
    @Override
    public Optional<PurchaseResponseDetailDto> findPurchaseDetailById(Long id) {
        return purchaseRepository.findById(id)
                .map(this::toDetailDto);
    }

    /** 구매서 상세 정보 조회 관련 메서드 **/
    // Entity -> 상세 조회용 DTO 변환 메소드
    private PurchaseResponseDetailDto toDetailDto(Purchase purchase) {
        return PurchaseResponseDetailDto.builder()
                .id(purchase.getId())
                .date(purchase.getDate())
                .clientId(purchase.getClient().getId())
                .clientCode(purchase.getClient().getCode())
                .clientName(purchase.getClient().getPrintClientName())
                .managerId(purchase.getManager().getId())
                .managerCode(purchase.getManager().getEmployeeNumber())
                .managerName(purchase.getManager().getLastName() + purchase.getManager().getFirstName())
                .warehouseId(purchase.getReceivingWarehouse().getId())
                .warehouseCode(purchase.getReceivingWarehouse().getCode())
                .warehouseName(purchase.getReceivingWarehouse().getName())
                .exchangeRate(purchase.getCurrency().getExchangeRate())
                .vatCode(vatTypeService.vatTypeGet(purchase.getVatId()).getVatTypeCode())
                .vatName(vatTypeService.vatTypeGet(purchase.getVatId()).getVatTypeName())
                .journalEntryCode(purchase.getJournalEntryCode())
                .electronicTaxInvoiceStatus(purchase.getElectronicTaxInvoiceStatus().toString())
                .currencyId(purchase.getCurrency().getId())
                .currency(purchase.getCurrency().getName())
                .remarks(purchase.getRemarks())
                .status(purchase.getStatus().toString())
                .purchaseDetails(toItemDetailDtoList(purchase.getPurchaseDetails()))
                .build();
    }

    private List<PurchaseItemDetailDto> toItemDetailDtoList(List<PurchaseDetail> details) {
        return details.stream()
                .map(this::toItemDetailDto)
                .collect(Collectors.toList());
    }

    private PurchaseItemDetailDto toItemDetailDto(PurchaseDetail detail) {
        Product product = detail.getProduct();
        return PurchaseItemDetailDto.builder()
                .productId(product.getId())
                .productCode(product.getCode())
                .productName(product.getName())
                .standard(product.getStandard())
                .price(product.getPurchasePrice())
                .quantity(detail.getQuantity())
                .supplyPrice(detail.getSupplyPrice())
                .localAmount(detail.getLocalAmount())
                .vat(detail.getVat())
                .remarks(detail.getRemarks())
                .build();
    }

    /**
     * 구매서 등록
     * @param createDto
     * @return
     */
    @Override
    public PurchaseResponseDetailDto createPurchase(PurchaseCreateDto createDto) {
        try {
            Purchase purchase = toEntity(createDto);
            purchase = purchaseRepository.save(purchase);
            return toDetailDto(purchase);
        } catch (Exception e) {
            log.error("구매서 생성 실패: ", e);
            return null;
        }
    }


    /** 구매서 등록 관련 메서드 **/
    // 구매서 등록 DTO -> Entity 변환 메소드
    private Purchase toEntity(PurchaseCreateDto dto) {
        Purchase purchase = Purchase.builder()
                .client(clientRepository.findById(dto.getClientId())
                        .orElseThrow(() -> new RuntimeException("거래처 정보를 찾을 수 없습니다.")))
                .manager(employeeRepository.findById(dto.getManagerId())
                        .orElseThrow(() -> new RuntimeException("담당자 정보를 찾을 수 없습니다.")))
                .receivingWarehouse(warehouseRepository.findById(dto.getWarehouseId())
                        .orElseThrow(() -> new RuntimeException("창고 정보를 찾을 수 없습니다.")))
                .currency(currencyRepository.findById(dto.getCurrencyId())
                        .orElseThrow(() -> new RuntimeException("통화 정보를 찾을 수 없습니다.")))
                .vatId(dto.getVatId())
                .journalEntryCode(dto.getJournalEntryCode())
                .remarks(dto.getRemarks())
                .build();

        return getPurchase(dto, purchase);
    }

    private Purchase getPurchase(PurchaseCreateDto dto, Purchase purchase) {
        dto.getItems().forEach(item -> {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("품목 정보를 찾을 수 없습니다."));

            BigDecimal supplyPrice = BigDecimal.valueOf(item.getQuantity()).multiply(product.getPurchasePrice());

            VatAmountWithSupplyAmountDTO vatAmountWithSupplyAmountDTO = new VatAmountWithSupplyAmountDTO();
            vatAmountWithSupplyAmountDTO.setSupplyAmount(supplyPrice);
            BigDecimal localAmount = null;
            BigDecimal vat = null;  // 외화의 경우 부가세 계산 안 함
            if(purchase.getCurrency().getCode().equals("KRW")) {
                vat = vatTypeService.vatAmountCalculate(vatAmountWithSupplyAmountDTO);
            } else {
                // 원화 금액 계산
                localAmount = supplyPrice.multiply(purchase.getCurrency().getExchangeRate());
            }

            PurchaseDetail detail = PurchaseDetail.builder()
                    .product(product)
                    .quantity(item.getQuantity())
                    .supplyPrice(supplyPrice)
                    .localAmount(localAmount)
                    .vat(vat)
                    .remarks(item.getRemarks())
                    .build();

            purchase.addPurchaseDetail(detail);
        });
        return purchase;
    }

    /**
     * 구매서 수정
     * @param id
     * @param updateDto
     * @return
     */
    @Override
    public PurchaseResponseDetailDto updatePurchase(Long id, PurchaseCreateDto updateDto) {
        try {
            Purchase purchase = purchaseRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("해당 구매서 정보를 찾을 수 없습니다."));

            if (updateDto.getManagerId() != null) {
                Employee manager = employeeRepository.findById(updateDto.getManagerId())
                        .orElseThrow(() -> new RuntimeException("해당 담당자 정보를 찾을 수 없습니다."));
                purchase.setManager(manager);
            }

            if (updateDto.getWarehouseId() != null) {
                Warehouse warehouse = warehouseRepository.findById(updateDto.getWarehouseId())
                        .orElseThrow(() -> new RuntimeException("해당 창고 정보를 찾을 수 없습니다."));
                purchase.setReceivingWarehouse(warehouse);
            }

            // 발주요청 일자, 납기일자 수정
            purchase.setDate(updateDto.getDate() != null ? updateDto.getDate() : purchase.getDate());

            // 통화 수정
            if (updateDto.getCurrencyId() != null) {
                purchase.setCurrency(currencyRepository.findById(updateDto.getCurrencyId())
                        .orElseThrow(() -> new RuntimeException("통화 정보를 찾을 수 없습니다.")));
            }

            // 부가세 적용 수정
            purchase.setVatId(updateDto.getVatId() != null ? updateDto.getVatId() : purchase.getVatId());

            purchase.getPurchaseDetails().clear();  // 기존 항목을 제거

            // 발주 상세 정보 업데이트 - 등록 관련 메서드의 getPurchaseRequest 메서드 사용
            Purchase newPurchase = getPurchase(updateDto, purchase);

            Purchase updatePurchase = purchaseRepository.save(newPurchase);

            return toDetailDto(updatePurchase);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 요청입니다.: " + e.getMessage());
        } catch (RuntimeException e) {
            log.error("구매서 수정 중 오류 발생: ", e);
            throw new RuntimeException("구매서 수정 중 오류가 발생했습니다.");
        }
    }

    /**
     * 구매서 삭제
     * @param id
     * @return
     */
    @Override
    public String deletePurchase(Long id) {
        try{
            Purchase purchase = purchaseRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("해당 구매서를 찾을 수 없습니다."));
            purchaseRepository.delete(purchase);
            return "구매서가 삭제되었습니다.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        } catch (RuntimeException e) {
            return "구매서 삭제 중 오류가 발생했습니다.";
        }
    }
}

