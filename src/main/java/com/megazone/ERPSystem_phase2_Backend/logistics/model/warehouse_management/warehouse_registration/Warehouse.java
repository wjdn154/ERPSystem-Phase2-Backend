package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_registration;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity (name = "Warehouse")
@Table (name = "warehouse")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false) // 고유식별자
    private Long id;


    @ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", nullable = false) // 주소정보
    @ToString.Exclude
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "warehouse_type_id", nullable = false) // 창고유형
    @ToString.Exclude
    private WarehouseType warehouseType;

    @OneToMany(mappedBy = "warehouse", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<WarehouseHierarchyGroup> warehouseHierarchyGroup = new ArrayList<>();

    @Column(name = "warehouse_code", nullable = false) // 창고코드
    private String code;

    @Column(nullable = false) // 창고명
    private String name;

    @Column(nullable = false) // 생산공정명
    private String productionProcess;

    @Column(nullable = false) // 사용여부(사용, 미사용)
    private Boolean isActive;

}
