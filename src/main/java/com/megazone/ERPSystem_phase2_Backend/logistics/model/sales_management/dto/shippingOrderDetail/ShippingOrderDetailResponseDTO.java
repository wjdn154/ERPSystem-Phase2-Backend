package com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.dto.shippingOrderDetail;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.ShippingOrderDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingOrderDetailResponseDTO {

    private Long id;                   // 출하 지시서 상세 ID
    private Long shippingOrderId;      // 출하 지시서 ID
    private Long clientId;
    private String representativeName;
    private Long managerId;
    private String managerName;
    private String shippingDate;
    private String date;
    private Long productId;            // 품목 ID
    private String productCode;
    private String productName;        // 품목명
    private Integer quantity;          // 출하 수량
    private String remarks;            // 비고

    public static ShippingOrderDetailResponseDTO fromEntity(ShippingOrderDetail detail) {
        return new ShippingOrderDetailResponseDTO(
                detail.getId(),
                detail.getShippingOrder().getId(),
                detail.getShippingOrder().getClient().getId(),
                detail.getShippingOrder().getClient().getRepresentativeName(),
                detail.getShippingOrder().getManager().getId(),
                detail.getShippingOrder().getManager().getLastName() + detail.getShippingOrder().getManager().getFirstName(),
                detail.getShippingOrder().getShippingDate().toString(),
                detail.getShippingOrder().getDate().toString(),
                detail.getProduct().getId(),
                detail.getProduct().getCode(),
                detail.getProduct().getName(),
                detail.getQuantity(),
                detail.getRemarks()
        );
    }
}
