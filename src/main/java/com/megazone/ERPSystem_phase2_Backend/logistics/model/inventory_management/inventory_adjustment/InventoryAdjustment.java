package com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryAdjustment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inventory_inspection_id", nullable = false)
    private InventoryInspection inventoryInspection;  // 실사 정보와 연관

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    // 재고 조정에서 조정된 품목들에 대한 정보
    @OneToMany(mappedBy = "inventoryAdjustment", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<InventoryAdjustmentDetail> adjustmentDetails = new ArrayList<>();

    @Column(name = "adjustment_date", nullable = false)
    private LocalDate adjustmentDate;

    @Column(name = "comment")
    private String comment;

}
