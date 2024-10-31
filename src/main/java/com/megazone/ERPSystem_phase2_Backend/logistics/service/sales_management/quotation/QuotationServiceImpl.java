package com.megazone.ERPSystem_phase2_Backend.logistics.service.sales_management.quotation;


import com.megazone.ERPSystem_phase2_Backend.Integrated.model.dashboard.RecentActivity;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.dashboard.enums.ActivityType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.ModuleType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.NotificationType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.PermissionType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.repository.dashboard.RecentActivityRepository;
import com.megazone.ERPSystem_phase2_Backend.Integrated.service.notification.NotificationService;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatAmountWithSupplyAmountDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.client.ClientRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.sales_and_purchase_voucher_entry.VatTypeService;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.SearchDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.Quotation;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.QuotationDetail;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.quotation.QuotationCreateDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.quotation.QuotationResponseDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.quotation.QuotationResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management.CurrencyRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.sales_management.quotation.QuotationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class QuotationServiceImpl implements QuotationService {

    private final QuotationRepository quotationRepository;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final WarehouseRepository warehouseRepository;
    private final CurrencyRepository currencyRepository;
    private final ProductRepository productRepository;
    private final VatTypeService vatTypeService;
    private final RecentActivityRepository recentActivityRepository;
    private final NotificationService notificationService;



    @Override
    public List<QuotationResponseDto> findAllQuotations(SearchDTO dto) {
        List<Quotation> quotations;

        // dto가 null이거나 조건이 모두 null일 경우 모든 발주서 조회
        if (dto == null || (dto.getStartDate() == null && dto.getEndDate() == null && dto.getClientId() == null && dto.getState() == null)) {
            quotations = quotationRepository.findAll(); // 전체 발주서 조회
        } else {
            // 조건이 있는 경우 QueryDSL을 사용하여 검색
            quotations = quotationRepository.findBySearch(dto);
        }

        // 발주서가 없는 경우 빈 리스트 반환
        return quotations.isEmpty()
                ? Collections.emptyList()
                : quotations.stream()
                .map(this::toListDto)
                .toList();
    }

    /** 견적서 목록 조회 관련 메서드 **/
    // Entity -> 견적서 목록 조회용 DTO 변환 메소드
    private QuotationResponseDto toListDto(Quotation quotation) {

        return QuotationResponseDto.builder()
                .id(quotation.getId())
                .clientName(quotation.getClient().getPrintClientName())
                .date(quotation.getDate())
                .productName(getProductNameWithCount(quotation))
                .warehouseName(quotation.getShippingWarehouse().getName())
                .vatName(vatTypeService.vatTypeGet(quotation.getVatId()).getVatTypeName())
                .totalPrice(getTotalPrice(quotation))
                .totalQuantity(getTotalQuantity(quotation))
                .status(quotation.getState().toString())
                .build();
    }

    private BigDecimal getTotalPrice(Quotation quotation) {
        return quotation.getQuotationDetails().stream()
                .map(QuotationDetail::getSupplyPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Integer getTotalQuantity(Quotation quotation) {
        return quotation.getQuotationDetails().stream()
                .map(QuotationDetail::getQuantity)
                .reduce(0, Integer::sum);
    }

    private String getProductNameWithCount(Quotation quotation) {
        List<QuotationDetail> details = quotation.getQuotationDetails();

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
    public Optional<QuotationResponseDetailDto> findQuotationDetailById(Long id) {
        return quotationRepository.findById(id)
                .map(this::toDetailDto);
    }

    /** 견적서 상세 정보 조회 관련 메서드 **/
    // Entity -> 상세 조회용 DTO 변환 메소드
    private QuotationResponseDetailDto toDetailDto(Quotation quotation) {
        return QuotationResponseDetailDto.builder()
                .id(quotation.getId())
                .date(quotation.getDate())
                .clientId(quotation.getClient().getId())
                .clientCode(quotation.getClient().getCode())
                .clientName(quotation.getClient().getPrintClientName())
                .managerId(quotation.getManager().getId())
                .managerCode(quotation.getManager().getEmployeeNumber())
                .managerName(quotation.getManager().getLastName() + quotation.getManager().getFirstName())
                .warehouseId(quotation.getShippingWarehouse().getId())
                .warehouseCode(quotation.getShippingWarehouse().getCode())
                .warehouseName(quotation.getShippingWarehouse().getName())
                .exchangeRate(quotation.getCurrency().getExchangeRate())
                .vatId(quotation.getVatId())
                .vatCode(vatTypeService.vatTypeGet(quotation.getVatId()).getVatTypeCode())
                .vatName(vatTypeService.vatTypeGet(quotation.getVatId()).getVatTypeName())
                .journalEntryCode(quotation.getJournalEntryCode())
                .electronicTaxInvoiceStatus(quotation.getElectronicTaxInvoiceStatus().toString())
                .currencyId(quotation.getCurrency().getId())
                .currency(quotation.getCurrency().getName())
                .remarks(quotation.getRemarks())
                .status(quotation.getState().toString())
                .quotationDetails(toItemDetailDtoList(quotation.getQuotationDetails()))
                .build();
    }

    private List<QuotationResponseDetailDto.QuotationItemDetailDto> toItemDetailDtoList(List<QuotationDetail> details) {
        return details.stream()
                .map(this::toItemDetailDto)
                .collect(Collectors.toList());
    }

    private QuotationResponseDetailDto.QuotationItemDetailDto toItemDetailDto(QuotationDetail detail) {
        Product product = detail.getProduct();
        return QuotationResponseDetailDto.QuotationItemDetailDto.builder()
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
    public QuotationResponseDetailDto createQuotation(QuotationCreateDto createDto) {
        try {
            Quotation quotation = toEntity(createDto);
            quotation = quotationRepository.save(quotation);

            recentActivityRepository.save(RecentActivity.builder()
                    .activityDescription("신규 견적서 등록 : " + quotation.getDate() + " -" + quotation.getId())
                    .activityType(ActivityType.LOGISTICS)
                    .activityTime(LocalDateTime.now())
                    .build());
            notificationService.createAndSendNotification(
                    ModuleType.LOGISTICS,
                    PermissionType.USER,
                    "신규 견적서 (" + quotation.getDate() + " -" + quotation.getId() + ")가 등록되었습니다.",
                    NotificationType.NEW_ENTRY
            );

            return toDetailDto(quotation);
        } catch (Exception e) {
            log.error("견적서 생성 실패: ", e);
            return null;
        }
    }

    /** 견적서 등록 관련 메서드 **/
    // 견적서 등록 DTO -> Entity 변환 메소드
    private Quotation toEntity(QuotationCreateDto dto) {
        Quotation quotation = Quotation.builder()
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
                .journalEntryCode(dto.getJournalEntryCode())
                .remarks(dto.getRemarks())
                .build();

        return getQuotation(dto, quotation);
    }

    private Quotation getQuotation(QuotationCreateDto dto, Quotation quotation) {

        dto.getItems().forEach(item -> {
            if (item.getProductId() == null) {
                throw new IllegalArgumentException("품목 ID가 제공되지 않았습니다.");
            }

            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("품목 정보를 찾을 수 없습니다. ID: " + item.getProductId()));

            BigDecimal supplyPrice = BigDecimal.valueOf(item.getQuantity()).multiply(product.getSalesPrice());

            VatAmountWithSupplyAmountDTO vatAmountWithSupplyAmountDTO = new VatAmountWithSupplyAmountDTO();
            vatAmountWithSupplyAmountDTO.setSupplyAmount(supplyPrice);
            vatAmountWithSupplyAmountDTO.setVatTypeId(quotation.getVatId());

            BigDecimal localAmount = null;
            BigDecimal vat = null;

            if (quotation.getCurrency().getId() == 6) {
                vat = vatTypeService.vatAmountCalculate(vatAmountWithSupplyAmountDTO);
                System.out.println("vat: " + vat);
            } else if (quotation.getCurrency().getExchangeRate() != null) {
                localAmount = supplyPrice.multiply(quotation.getCurrency().getExchangeRate());
            } else {
                throw new RuntimeException("환율 정보가 없습니다.");
            }

            QuotationDetail detail = QuotationDetail.builder()
                    .product(product)
                    .quantity(item.getQuantity())
                    .supplyPrice(supplyPrice)
                    .localAmount(localAmount)
                    .vat(vat)
                    .remarks(item.getRemarks())
                    .build();
            quotation.addQuotationDetail(detail);
        });
        return quotation;
    }

    @Override
    public QuotationResponseDetailDto updateQuotation(Long id, QuotationCreateDto updateDto) {
        try {
            Quotation quotation = quotationRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("해당 견적서 정보를 찾을 수 없습니다."));

            if (updateDto.getManagerId() != null) {
                Employee manager = employeeRepository.findById(updateDto.getManagerId())
                        .orElseThrow(() -> new RuntimeException("해당 담당자 정보를 찾을 수 없습니다."));
                quotation.setManager(manager);
            }

            if (updateDto.getWarehouseId() != null) {
                Warehouse warehouse = warehouseRepository.findById(updateDto.getWarehouseId())
                        .orElseThrow(() -> new RuntimeException("해당 창고 정보를 찾을 수 없습니다."));
                quotation.setShippingWarehouse(warehouse);
            }

            // 발주요청 일자, 납기일자 수정
            quotation.setDate(updateDto.getDate() != null ? updateDto.getDate() : quotation.getDate());

            // 통화 수정
            if (updateDto.getCurrencyId() != null) {
                quotation.setCurrency(currencyRepository.findById(updateDto.getCurrencyId())
                        .orElseThrow(() -> new RuntimeException("통화 정보를 찾을 수 없습니다.")));
            }

            // 부가세 적용 수정
            quotation.setVatId(updateDto.getVatId() != null ? updateDto.getVatId() : quotation.getVatId());
            quotation.setRemarks(updateDto.getRemarks() != null ? updateDto.getRemarks() : quotation.getRemarks());

            quotation.getQuotationDetails().clear();  // 기존 항목을 제거

            // 발주 상세 정보 업데이트 - 등록 관련 메서드의 getQuotationRequest 메서드 사용
            Quotation newQuotation = getQuotation(updateDto, quotation);

            Quotation updatedQuotation = quotationRepository.save(newQuotation);

            return toDetailDto(updatedQuotation);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 요청입니다.: " + e.getMessage());
        } catch (RuntimeException e) {
            log.error("견적서 수정 중 오류 발생: ", e);
            throw new RuntimeException("견적서 수정 중 오류가 발생했습니다.");
        }
    }

    @Override
    public String deleteQuotation(Long id) {
        try{
            Quotation quotation = quotationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("해당 구매서를 찾을 수 없습니다."));
            quotationRepository.delete(quotation);
            return "구매서가 삭제되었습니다.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        } catch (RuntimeException e) {
            return "구매서 삭제 중 오류가 발생했습니다.";
        }
    }

}
