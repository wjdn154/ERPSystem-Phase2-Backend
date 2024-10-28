package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.Client;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Department;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.ProductionRequest;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.enums.ProductionRequestType;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.enums.ProgressType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductionRequestDetailDTO {
    private Long id;
    private ProductionRequestType requestType;
    private ProgressType progressType;
    private String name;
    private Boolean isConfirmed;
    private String requestDate;
    private String deadlineOfCompletion;
    private String dueDateToProvide;
    private Long requestQuantity;
    private Long confirmedQuantity;
    private String remarks;
    private Long clientId;
    private Long departmentId;
    private Long productId;
    private Long requesterId;
    // 사용자에게 표시할 필드
    private String clientCode;      // 클라이언트 코드
    private String clientName;      // 클라이언트 이름
    private String departmentCode; // 부서번호
    private String departmentName;  // 부서 이름
    private String productCode;
    private String productGroupCode;// 폼목 그룹코드
    private String productGroupName;// 폼목 그룹명
    private String productName;     // 제품 이름
    private String requesterName;   // 요청자 이름
    private String requesterEmployeeNumber;

    // DTO로 변환하는 정적 메서드
    public static ProductionRequestDetailDTO fromEntity(ProductionRequest request) {
        return ProductionRequestDetailDTO.builder()
                .id(request.getId())
                .requestType(request.getRequestType())
                .progressType(request.getProgressType())
                .name(request.getName())
                .isConfirmed(request.getIsConfirmed())
                .requestDate(request.getRequestDate().toString())
                .deadlineOfCompletion(request.getDeadlineOfCompletion().toString())
                .dueDateToProvide(request.getDueDateToProvide().toString())
                .requestQuantity(request.getRequestQuantity())
                .confirmedQuantity(request.getConfirmedQuantity() != null ? request.getConfirmedQuantity() : null)
                .remarks(request.getRemarks())
                .clientId(request.getClient() != null ? request.getClient().getId() : null)
                .clientCode(request.getClient() != null ? request.getClient().getCode() : "미지정")
                .clientName(request.getClient() != null ? request.getClient().getPrintClientName() : "미지정")
                .departmentId(request.getProductionDepartment() != null ? request.getProductionDepartment().getId() : null)
                .departmentCode(request.getProductionDepartment() != null ? request.getProductionDepartment().getDepartmentCode() : "미지정")
                .departmentName(request.getProductionDepartment() != null ? request.getProductionDepartment().getDepartmentName() : "미지정")
                .productId(request.getProduct() != null ? request.getProduct().getId() : null)
                .productCode(request.getProduct() != null ? request.getProduct().getCode() : "미지정")
                .productGroupCode(request.getProduct() != null ? request.getProduct().getProductGroup().getCode() : "미지정")
                .productGroupName(request.getProduct() != null ? request.getProduct().getProductGroup().getName() : "미지정")
                .productName(request.getProduct() != null ? request.getProduct().getName() : "미지정")
                .requesterId(request.getRequester() != null ? request.getRequester().getId() : null)
                .requesterName(request.getRequester() != null
                        ? request.getRequester().getLastName() + " " + request.getRequester().getFirstName()
                        : "미지정")
                .requesterEmployeeNumber(request.getRequester() != null ? request.getRequester().getEmployeeNumber() : "미지정")
                .build();
    }


    // Entity로 변환하는 정적 메서드
    public ProductionRequest toEntity() {
        Client client = this.clientId != null ? new Client() : null;
        Department department = this.departmentId != null ? new Department() : null;
        Product product = this.productId != null ? new Product() : null;
        Employee requester = this.requesterId != null ? new Employee() : null;

        return ProductionRequest.builder()
                .id(this.id)
                .requestType(this.requestType)
                .progressType(this.progressType)
                .name(this.name)
                .isConfirmed(this.isConfirmed)
                .requestDate(LocalDate.parse(this.requestDate))
                .deadlineOfCompletion(LocalDate.parse(this.deadlineOfCompletion))
                .dueDateToProvide(LocalDate.parse(this.dueDateToProvide))
                .requestQuantity(this.requestQuantity)
                .confirmedQuantity(this.confirmedQuantity != null ? this.confirmedQuantity : 0L)
                .remarks(this.remarks)
                .client(client)
                .productionDepartment(department)
                .product(product)
                .requester(requester)
                .build();
    }
}