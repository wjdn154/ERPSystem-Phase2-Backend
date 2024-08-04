package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_registration;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 식별자

    @Column(nullable = false)
    private String name; // 창고유형
}
