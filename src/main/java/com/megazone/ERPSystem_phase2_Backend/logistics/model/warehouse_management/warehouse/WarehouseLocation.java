package com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse;


import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.Company;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.Inventory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseLocation {

    // 고유 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @OneToMany(mappedBy = "warehouseLocation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inventory> inventories;  // 양방향 매핑

    @Column(name = "location_name", nullable = false, unique = true)
    private String locationName;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;
}
