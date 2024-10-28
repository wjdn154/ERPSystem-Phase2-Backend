package com.megazone.ERPSystem_phase2_Backend.logistics.service.sales_management.sale;


import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.sales_and_purchase_voucher_entry.dto.VatAmountWithSupplyAmountDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.client.ClientRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.sales_and_purchase_voucher_entry.VatTypeService;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.SearchDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.Sale;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.SaleDetail;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.sale.SaleCreateDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.sale.SaleResponseDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.sale.SaleResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management.CurrencyRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.sales_management.sale.SaleRepository;
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
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final WarehouseRepository warehouseRepository;
    private final CurrencyRepository currencyRepository;
    private final ProductRepository productRepository;
    private final VatTypeService vatTypeService;



    @Override
    public List<SaleResponseDto> findAllSales(SearchDTO dto) {
        List<Sale> sales;

        // dto가 null이거나 조건이 모두 null일 경우 모든 발주서 조회
        if (dto == null || (dto.getStartDate() == null && dto.getEndDate() == null && dto.getClientId() == null && dto.getState() == null)) {
            sales = saleRepository.findAll(); // 전체 발주서 조회
        } else {
            // 조건이 있는 경우 QueryDSL을 사용하여 검색
            sales = saleRepository.findBySearch(dto);
        }

        // 발주서가 없는 경우 빈 리스트 반환
        return sales.isEmpty()
                ? Collections.emptyList()
                : sales.stream()
                .map(this::toListDto)
                .toList();
    }

    /** 견적서 목록 조회 관련 메서드 **/
    // Entity -> 견적서 목록 조회용 DTO 변환 메소드
    private SaleResponseDto toListDto(Sale sale) {

        return SaleResponseDto.builder()
                .id(sale.getId())
                .clientName(sale.getClient().getPrintClientName())
                .date(sale.getDate())
                .productName(getProductNameWithCount(sale))
                .warehouseName(sale.getShippingWarehouse().getName())
                .vatName(vatTypeService.vatTypeGet(sale.getVatId()).getVatTypeName())
                .totalPrice(getTotalPrice(sale))
                .totalQuantity(getTotalQuantity(sale))
                .status(sale.getState().toString())
                .accountingReflection(sale.getAccountingReflection())
                .build();
    }

    private BigDecimal getTotalPrice(Sale sale) {
        return sale.getSaleDetails().stream()
                .map(SaleDetail::getSupplyPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Integer getTotalQuantity(Sale sale) {
        return sale.getSaleDetails().stream()
                .map(SaleDetail::getQuantity)
                .reduce(0, Integer::sum);
    }

    private String getProductNameWithCount(Sale sale) {
        List<SaleDetail> details = sale.getSaleDetails();

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
    public Optional<SaleResponseDetailDto> findSaleDetailById(Long id) {
        return saleRepository.findById(id)
                .map(this::toDetailDto);
    }

    /** 견적서 상세 정보 조회 관련 메서드 **/
    // Entity -> 상세 조회용 DTO 변환 메소드
    private SaleResponseDetailDto toDetailDto(Sale sale) {
        return SaleResponseDetailDto.builder()
                .id(sale.getId())
                .date(sale.getDate())
                .clientId(sale.getClient().getId())
                .clientCode(sale.getClient().getCode())
                .clientName(sale.getClient().getPrintClientName())
                .managerId(sale.getManager().getId())
                .managerCode(sale.getManager().getEmployeeNumber())
                .managerName(sale.getManager().getLastName() + sale.getManager().getFirstName())
                .warehouseId(sale.getShippingWarehouse().getId())
                .warehouseCode(sale.getShippingWarehouse().getCode())
                .warehouseName(sale.getShippingWarehouse().getName())
                .exchangeRate(sale.getCurrency().getExchangeRate())
                .vatId(sale.getVatId())
                .vatCode(vatTypeService.vatTypeGet(sale.getVatId()).getVatTypeCode())
                .vatName(vatTypeService.vatTypeGet(sale.getVatId()).getVatTypeName())
                .journalEntryCode(sale.getJournalEntryCode())
                .electronicTaxInvoiceStatus(sale.getElectronicTaxInvoiceStatus().toString())
                .currencyId(sale.getCurrency().getId())
                .currency(sale.getCurrency().getName())
                .remarks(sale.getRemarks())
                .status(sale.getState().toString())
                .accountingReflection(sale.getAccountingReflection())
                .saleDetails(toItemDetailDtoList(sale.getSaleDetails()))
                .build();
    }

    private List<SaleResponseDetailDto.SaleItemDetailDto> toItemDetailDtoList(List<SaleDetail> details) {
        return details.stream()
                .map(this::toItemDetailDto)
                .collect(Collectors.toList());
    }

    private SaleResponseDetailDto.SaleItemDetailDto toItemDetailDto(SaleDetail detail) {
        Product product = detail.getProduct();
        return SaleResponseDetailDto.SaleItemDetailDto.builder()
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
    public SaleResponseDetailDto createSale(SaleCreateDto createDto) {
        try {
            Sale sale = toEntity(createDto);
            sale = saleRepository.save(sale);
            return toDetailDto(sale);
        } catch (Exception e) {
            log.error("견적서 생성 실패: ", e);
            return null;
        }
    }

    /** 견적서 등록 관련 메서드 **/
    // 견적서 등록 DTO -> Entity 변환 메소드
    private Sale toEntity(SaleCreateDto dto) {
        Sale sale = Sale.builder()
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

        return getSale(dto, sale);
    }

    private Sale getSale(SaleCreateDto dto, Sale sale) {
        if (sale.getCurrency() == null) {
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

            if ("KRW".equals(sale.getCurrency().getCode())) {
                vat = vatTypeService.vatAmountCalculate(vatAmountWithSupplyAmountDTO);
            } else if (sale.getCurrency().getExchangeRate() != null) {
                localAmount = supplyPrice.multiply(sale.getCurrency().getExchangeRate());
            } else {
                throw new RuntimeException("환율 정보가 없습니다.");
            }

            SaleDetail detail = SaleDetail.builder()
                    .product(product)
                    .quantity(item.getQuantity())
                    .supplyPrice(supplyPrice)
                    .localAmount(localAmount)
                    .vat(vat)
                    .remarks(item.getRemarks())
                    .build();
            sale.addSaleDetail(detail);
        });
        return sale;
    }

    @Override
    public SaleResponseDetailDto updateSale(Long id, SaleCreateDto updateDto) {
        try {
            Sale sale = saleRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("해당 견적서 정보를 찾을 수 없습니다."));

            if (updateDto.getManagerId() != null) {
                Employee manager = employeeRepository.findById(updateDto.getManagerId())
                        .orElseThrow(() -> new RuntimeException("해당 담당자 정보를 찾을 수 없습니다."));
                sale.setManager(manager);
            }

            if (updateDto.getWarehouseId() != null) {
                Warehouse warehouse = warehouseRepository.findById(updateDto.getWarehouseId())
                        .orElseThrow(() -> new RuntimeException("해당 창고 정보를 찾을 수 없습니다."));
                sale.setShippingWarehouse(warehouse);
            }

            // 발주요청 일자, 납기일자 수정
            sale.setDate(updateDto.getDate() != null ? updateDto.getDate() : sale.getDate());

            // 통화 수정
            if (updateDto.getCurrencyId() != null) {
                sale.setCurrency(currencyRepository.findById(updateDto.getCurrencyId())
                        .orElseThrow(() -> new RuntimeException("통화 정보를 찾을 수 없습니다.")));
            }

            // 부가세 적용 수정
            sale.setVatId(updateDto.getVatId() != null ? updateDto.getVatId() : sale.getVatId());

            sale.setAccountingReflection(updateDto.getAccountingReflection());
            sale.setRemarks(updateDto.getRemarks());
            sale.getSaleDetails().clear();  // 기존 항목을 제거

            // 발주 상세 정보 업데이트 - 등록 관련 메서드의 getSaleRequest 메서드 사용
            Sale newSale = getSale(updateDto, sale);

            Sale updatedSale = saleRepository.save(newSale);

            return toDetailDto(updatedSale);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 요청입니다.: " + e.getMessage());
        } catch (RuntimeException e) {
            log.error("견적서 수정 중 오류 발생: ", e);
            throw new RuntimeException("견적서 수정 중 오류가 발생했습니다.");
        }
    }

    @Override
    public String deleteSale(Long id) {
        try{
            Sale sale = saleRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("해당 구매서를 찾을 수 없습니다."));
            saleRepository.delete(sale);
            return "구매서가 삭제되었습니다.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        } catch (RuntimeException e) {
            return "구매서 삭제 중 오류가 발생했습니다.";
        }
    }

}
