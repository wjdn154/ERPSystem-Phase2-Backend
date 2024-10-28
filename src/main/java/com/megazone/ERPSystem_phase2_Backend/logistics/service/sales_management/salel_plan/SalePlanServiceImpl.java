package com.megazone.ERPSystem_phase2_Backend.logistics.service.sales_management.salel_plan;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.SearchDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.SalePlan;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.SalePlanDetail;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.sale_plan.SalePlanResponseDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.sales_management.sale_plan.SalePlanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SalePlanServiceImpl implements SalePlanService {

    private final SalePlanRepository salePlanRepository;

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

}
