package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.Client;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Department;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.Orders;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.ProductionRequest;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.enums.ProductionRequestType;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.enums.ProgressType;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductionRequestListDTO {

    private Long id;

    private ProductionRequestType requestType; // 생산의뢰구분 (Enum)

    private ProgressType progressType; // 진행상태구분 (Enum)

    private String name; // 생산의뢰명

    private Boolean isConfirmed; // 확정여부

    private LocalDate requestDate; // 의뢰등록일자

    private LocalDate deadlineOfCompletion; // 완료요청일자 (내부부서가 생산부서에 요청하는 생산 완료일자)

    private LocalDate dueDateToProvide; // 납기일 (고객에게 제품을 납품해야 하는 최종 기한)

    private Long clientId; // 거래처 (고객사명 등)

    private Long departmentId; // 생산부서 (요청 품목을 생산하는 부서명)

    private Long requestQuantity; // 요청수량

    private Long confirmedQuantity; // 확정수량 (생산 능력에 따라 결정된 수량)

    private List<Long> MpsIds; // 연관된 주문생산계획 Ids

    private Long productId; // 제품 (제품명, 제품 번호 등)

    private Long salesOrderId; // 영업 주문 (수주 번호, 판매 단위, P/O No. 등)

    private Long requesterId; // 요청자 (요청한 사람의 이름 또는 부서)

    private String remarks; // 특이사항

    // 엔티티 -> DTO 변환
    public static ProductionRequestListDTO fromEntity(ProductionRequest entity) {
        return ProductionRequestListDTO.builder()
                .id(entity.getId())
                .requestType(entity.getRequestType())
                .progressType(entity.getProgressType())
                .name(entity.getName())
                .isConfirmed(entity.getIsConfirmed())
                .requestDate(entity.getRequestDate())
                .deadlineOfCompletion(entity.getDeadlineOfCompletion())
                .dueDateToProvide(entity.getDueDateToProvide())
                .requestQuantity(entity.getRequestQuantity())
                .confirmedQuantity(entity.getConfirmedQuantity())
                .remarks(entity.getRemarks())
                .clientId(entity.getClient() != null ? entity.getClient().getId() : null)
                .departmentId(entity.getProductionDepartment() != null ? entity.getProductionDepartment().getId() : null)
                .productId(entity.getProduct() != null ? entity.getProduct().getId() : null)
                .salesOrderId(entity.getSalesOrder() != null ? entity.getSalesOrder().getId() : null)
                .requesterId(entity.getRequester() != null ? entity.getRequester().getId() : null)
                .build();
    }

    public static ProductionRequest createEntity(
            ProductionRequestListDTO dto,
            Client client,
            Department department,
            Product product,
            Employee requester
    ) {
        return ProductionRequest.builder()
                .id(dto.getId())
                .requestType(dto.getRequestType())
                .progressType(dto.getProgressType())
                .name(dto.getName())
                .isConfirmed(dto.getIsConfirmed())
                .requestDate(dto.getRequestDate())
                .deadlineOfCompletion(dto.getDeadlineOfCompletion())
                .dueDateToProvide(dto.getDueDateToProvide())
                .requestQuantity(dto.getRequestQuantity())
                .confirmedQuantity(dto.getConfirmedQuantity())
                .remarks(dto.getRemarks())
                .client(client)
                .productionDepartment(department)
                .product(product)
                .requester(requester)
                .salesOrder(dto.getSalesOrderId() != null ? Orders.builder().id(dto.getSalesOrderId()).build() : null)
                .build();
    }

}
