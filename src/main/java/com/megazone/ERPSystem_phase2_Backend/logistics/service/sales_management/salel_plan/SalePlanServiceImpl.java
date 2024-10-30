package com.megazone.ERPSystem_phase2_Backend.logistics.service.sales_management.salel_plan;

import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.client.ClientRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.service.voucher_entry.sales_and_purchase_voucher_entry.VatTypeService;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.SearchDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.SalePlan;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.SalePlanDetail;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.sale_plan.SalePlanCreateDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.sale_plan.SalePlanResponseDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.sale_plan.SalePlanResponseDetailDto.SalePlanItemDetailDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.sale_plan.SalePlanResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.basic_information_management.warehouse.WarehouseRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.purchase_management.CurrencyRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.sales_management.sale_plan.SalePlanRepository;
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
public class SalePlanServiceImpl implements SalePlanService {

    private final SalePlanRepository salePlanRepository;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final WarehouseRepository warehouseRepository;
    private final CurrencyRepository currencyRepository;
    private final ProductRepository productRepository;
    private final VatTypeService vatTypeService;

    /**
     * 판매 계획 목록 조회
     *
     * @return
     */
    @Override
    public List<SalePlanResponseDto> findAllSalePlans(SearchDTO dto) {

        List<SalePlan> salePlans;

        // dto가 null이거나 모든 조건이 null일 경우 전체 판매 계획 조회
        if (dto == null || (dto.getStartDate() == null && dto.getEndDate() == null && dto.getClientId() == null)) {
            salePlans = salePlanRepository.findAll(); // 전체 판매 계획 조회
        } else {
            // 조건이 있는 경우 QueryDSL을 사용하여 검색
            salePlans = salePlanRepository.findBySearch(dto);
        }

        // 판매 계획이 없는 경우 빈 리스트 반환
        return salePlans.isEmpty()
                ? Collections.emptyList()
                : salePlans.stream()
                .map(this::toListDto)
                .toList();
    }

    /**
     * 판매 계획 목록 조회 관련 메서드
     **/
// Entity -> 판매 계획 목록 조회용 DTO 변환 메소드
    private SalePlanResponseDto toListDto(SalePlan salePlan) {
        return SalePlanResponseDto.builder()
                .id(salePlan.getId())
                .clientName(salePlan.getClient().getPrintClientName())  // 거래처 이름
                .managerName(salePlan.getManager().getLastName() + salePlan.getManager().getFirstName())  // 담당자 이름
                .warehouseName(salePlan.getWarehouse().getName())  // 창고 이름
                .productName(getProductName(salePlan))  // 첫 번째 제품 이름과 "외 몇건" 추가
                .date(salePlan.getDate())
                .totalQuantity(getTotalQuantity(salePlan))
                .expectedSaleDate(salePlan.getExpectedSalesDate())
                .totalExpectedSales(getTotalExpectedSales(salePlan))  // 예상 총 판매 금액
                .build();
    }

    // 첫 번째 제품 이름과 외 몇건 처리
    private String getProductName(SalePlan salePlan) {
        List<SalePlanDetail> details = salePlan.getSalePlanDetails();

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
    private int getTotalQuantity(SalePlan salePlan) {
        return salePlan.getSalePlanDetails().stream()
                .mapToInt(SalePlanDetail::getQuantity)
                .sum();
    }

    // 예상 총 판매 금액 계산
    private BigDecimal getTotalExpectedSales(SalePlan salePlan) {
        return salePlan.getSalePlanDetails().stream()
                .map(SalePlanDetail::getExpectedSales)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 판매계획 상세 정보 조회
     * @param id
     * @return
     */
    public Optional<SalePlanResponseDetailDto> findSalePlanDetailById(Long id) {
        return salePlanRepository.findById(id)
                .map(this::toDetailDto);
    }

    /** 판매계획 상세 정보 조회 관련 메서드 **/
    // Entity -> 상세 조회용 DTO 변환 메소드
    private SalePlanResponseDetailDto toDetailDto(SalePlan salePlan) {
        return SalePlanResponseDetailDto.builder()
                .id(salePlan.getId())
                .date(salePlan.getDate())
                .expectedSalesDate(salePlan.getExpectedSalesDate())
                .clientId(salePlan.getClient().getId())
                .clientCode(salePlan.getClient().getCode())
                .clientName(salePlan.getClient().getPrintClientName())
                .managerId(salePlan.getManager().getId())
                .managerCode(salePlan.getManager().getEmployeeNumber())
                .managerName(salePlan.getManager().getLastName() + salePlan.getManager().getFirstName())
                .warehouseId(salePlan.getWarehouse().getId())
                .warehouseCode(salePlan.getWarehouse().getCode())
                .warehouseName(salePlan.getWarehouse().getName())
                .remarks(salePlan.getRemarks())
                .status(salePlan.getState().toString())
                .salePlanDetails(toItemDetailDtoList(salePlan.getSalePlanDetails()))
                .build();
    }

    private List<SalePlanItemDetailDto> toItemDetailDtoList(List<SalePlanDetail> details) {
        return details.stream()
                .map(this::toItemDetailDto)
                .collect(Collectors.toList());
    }

    private SalePlanItemDetailDto toItemDetailDto(SalePlanDetail detail) {
        Product product = detail.getProduct();
        return SalePlanResponseDetailDto.SalePlanItemDetailDto.builder()
                .productId(product.getId())
                .productCode(product.getCode())
                .productName(product.getName())
                .price(product.getSalesPrice())
                .quantity(detail.getQuantity())
                .expectedSales(detail.getExpectedSales())
                .remarks(detail.getRemarks())
                .build();
    }

    /**
     * 판매 계획 등록
     * @param createDto
     * @return
     */
    public SalePlanResponseDetailDto createSalePlan(SalePlanCreateDto createDto) {
        try {
            SalePlan salePlan = toEntity(createDto);
            salePlan = salePlanRepository.save(salePlan);
            return toDetailDto(salePlan);
        } catch (Exception e) {
            log.error("판매 계획 생성 실패: ", e);
            return null;
        }
    }

    /** 판매 계획 등록 관련 메서드 **/
    // 판매 계획 등록 DTO -> Entity 변환 메소드
    private SalePlan toEntity(SalePlanCreateDto dto) {
        SalePlan salePlan = SalePlan.builder()
                .client(clientRepository.findById(dto.getClientId())
                        .orElseThrow(() -> new RuntimeException("거래처 정보를 찾을 수 없습니다.")))
                .manager(employeeRepository.findById(dto.getManagerId())
                        .orElseThrow(() -> new RuntimeException("담당자 정보를 찾을 수 없습니다.")))
                .warehouse(warehouseRepository.findById(dto.getWarehouseId())
                        .orElseThrow(() -> new RuntimeException("창고 정보를 찾을 수 없습니다.")))
                .date(dto.getDate())
                .expectedSalesDate(dto.getExpectedSalesDate())
                .remarks(dto.getRemarks())
                .build();

        return getSalePlan(dto, salePlan);
    }

    private SalePlan getSalePlan(SalePlanCreateDto dto, SalePlan salePlan) {

        dto.getItems().forEach(item -> {
            if (item.getProductId() == null) {
                throw new IllegalArgumentException("품목 ID가 제공되지 않았습니다.");
            }

            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("품목 정보를 찾을 수 없습니다. ID: " + item.getProductId()));

            BigDecimal expectedSalesDate = BigDecimal.valueOf(item.getQuantity()).multiply(product.getSalesPrice());


            SalePlanDetail detail = SalePlanDetail.builder()
                    .product(product)
                    .quantity(item.getQuantity())
                    .expectedSales(expectedSalesDate)
                    .remarks(item.getRemarks())
                    .build();
            salePlan.addSalePlanDetail(detail);
        });
        return salePlan;
    }

    public SalePlanResponseDetailDto updateSalePlan(Long id, SalePlanCreateDto updateDto) {
        try {
            SalePlan salePlan = salePlanRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("해당 판매 계획 정보를 찾을 수 없습니다."));

            if (updateDto.getManagerId() != null) {
                Employee manager = employeeRepository.findById(updateDto.getManagerId())
                        .orElseThrow(() -> new RuntimeException("해당 담당자 정보를 찾을 수 없습니다."));
                salePlan.setManager(manager);
            }

            if (updateDto.getWarehouseId() != null) {
                Warehouse warehouse = warehouseRepository.findById(updateDto.getWarehouseId())
                        .orElseThrow(() -> new RuntimeException("해당 창고 정보를 찾을 수 없습니다."));
                salePlan.setWarehouse(warehouse);
            }

            // 발주요청 일자, 납기일자 수정
            salePlan.setDate(updateDto.getDate() != null ? updateDto.getDate() : salePlan.getDate());
            salePlan.setExpectedSalesDate(updateDto.getExpectedSalesDate());
            salePlan.setRemarks(updateDto.getRemarks());
            salePlan.getSalePlanDetails().clear();  // 기존 항목을 제거

            // 발주 상세 정보 업데이트 - 등록 관련 메서드의 getSaleRequest 메서드 사용
            SalePlan newSalePlan = getSalePlan(updateDto, salePlan);

            SalePlan updatedSalePlan = salePlanRepository.save(newSalePlan);

            return toDetailDto(updatedSalePlan);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 요청입니다.: " + e.getMessage());
        } catch (RuntimeException e) {
            log.error("판매 계획 수정 중 오류 발생: ", e);
            throw new RuntimeException("판매 계획 수정 중 오류가 발생했습니다.");
        }
    }

    public String deleteSalePlan(Long id) {
        try{
            SalePlan salePlan = salePlanRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("해당 판매계획를 찾을 수 없습니다."));
            salePlanRepository.delete(salePlan);
            return "판매계획가 삭제되었습니다.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        } catch (RuntimeException e) {
            return "판매계획 삭제 중 오류가 발생했습니다.";
        }
    }

}
