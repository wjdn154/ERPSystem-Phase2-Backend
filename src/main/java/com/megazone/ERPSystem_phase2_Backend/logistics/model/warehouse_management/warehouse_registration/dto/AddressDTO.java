package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_registration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private Long id;
    private String warehouseAddress;
    private String detailAddress;
    private String warehousePostalCode;
    private String warehousePlace;
}
