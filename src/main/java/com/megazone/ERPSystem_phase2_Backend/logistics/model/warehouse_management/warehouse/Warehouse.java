package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.enums.WarehouseType;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.EquipmentData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity (name = "warehouse")
@Table (name = "warehouse")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false) // 고유식별자
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = true) // 주소정보
    @ToString.Exclude
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WarehouseType warehouseType;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    private List<WarehouseHierarchyGroup> warehouseHierarchyGroup = new ArrayList<>();

    @Column(name = "warehouse_code", nullable = false) // 창고코드
    private String code;

    @Column(nullable = false) // 창고명
    private String name;

    @Column(nullable = false) // 생산공정명
    private String productionProcess;

    @Column(nullable = false) // 사용여부(사용, 미사용)
    private Boolean isActive;

    @OneToMany(mappedBy = "factory")
    private List<Workcenter> workcenters = new ArrayList<>();// 생산관리의 작업장 엔티티 중 factory와 연결

    @OneToMany(mappedBy = "factory")
    private List<EquipmentData> equipmentData = new ArrayList<>(); // 생산관리의 설비 엔티티 중 factory 와 연결
}
