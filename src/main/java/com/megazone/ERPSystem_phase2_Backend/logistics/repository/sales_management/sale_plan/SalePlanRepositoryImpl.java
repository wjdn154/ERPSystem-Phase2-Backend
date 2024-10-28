package com.megazone.ERPSystem_phase2_Backend.logistics.repository.sales_management.sale_plan;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management.dto.SearchDTO;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.QSalePlan;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.SalePlan;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.SaleState;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class SalePlanRepositoryImpl implements SalePlanRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<SalePlan> findBySearch(SearchDTO dto) {
        QSalePlan salePlan = QSalePlan.salePlan;

        return queryFactory
                .selectFrom(salePlan)
                .where(
                        buildSearchCondition(dto)  // 검색 조건 빌드
                )
                .fetch();
    }

    // 검색 조건을 동적으로 생성
    private BooleanBuilder buildSearchCondition(SearchDTO dto) {
        QSalePlan salePlan = QSalePlan.salePlan;

        BooleanBuilder condition = new BooleanBuilder();  // BooleanBuilder로 동적 조건 생성

        // 발주 날짜 조건
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            condition.and(salePlan.date.between(dto.getStartDate(), dto.getEndDate()));
        }

        // 거래처 코드 조건
        if (dto.getClientId() != null) {
            condition.and(salePlan.salePlanDetails.any().product.client.id.eq(dto.getClientId()));  // 거래처 코드 조건 추가
        }

        // 상태 조건 (String을 Enum으로 변환)
        if (dto.getState() != null && !dto.getState().isEmpty()) {

            // DTO에서 상태를 가져와 Enum으로 변환
            SaleState enumState = SaleState.valueOf(dto.getState().toUpperCase());

            // QueryDSL Enum 값 비교
            condition.and(salePlan.state.eq(enumState));
        }

        return condition;
    }
}
