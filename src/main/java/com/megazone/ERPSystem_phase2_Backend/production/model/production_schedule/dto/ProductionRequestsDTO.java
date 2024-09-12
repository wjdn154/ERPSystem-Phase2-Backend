package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto;

import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.enums.ProductionRequestsType;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.enums.ProgressType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductionRequestsDTO {

    private Long id; // PK

    private ProductionRequestsType requestsType; // 생산의뢰구분 (Enum)

    private ProgressType progressType; // 진행상태구분 (Enum)

    private String name; // 생산의뢰명

    private Boolean isConfirmed; // 확정여부

    private LocalDate requestDate; // 의뢰등록일자

    private LocalDate deadlineOfCompletion; // 완료요청일자 (내부부서가 생산부서에 요청하는 생산 완료일자)

    private LocalDate dueDateToProvide; // 납기일 (고객에게 제품을 납품해야 하는 최종 기한)

    private String client; // 거래처 (고객사명 등) TODO 연관

    private String productionDepartment; // 생산부서 (요청 품목을 생산하는 부서명) TODO 연관

    private BigDecimal requestQuantity; // 요청수량

    private BigDecimal confirmedQuantity; // 확정수량 (생산 능력에 따라 결정된 수량)

    private List<Long> planOfMakeToOrderIds; // 연관된 주문생산계획 Ids

    private String product; // 제품 (제품명, 제품 번호 등) TODO 연관

    private String salesOrder; // 영업 주문 (수주 번호, 판매 단위, P/O No. 등) TODO 연관

    private String requester; // 요청자 (요청한 사람의 이름 또는 부서) TODO 연관

    private String remarks; // 특이사항
}
