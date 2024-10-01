package com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment;


import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.inventory_management.inventory_adjustment.enums.InspectionStatus;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
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
@Table(name = "inventory_inspection")
public class InventoryInspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;  // 실사가 진행된 창고 정보

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;  // 실사 담당자

    @OneToMany(mappedBy = "inventoryInspection", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<InventoryInspectionDetail> details = new ArrayList<>();  // 실사 항목들

    @Column(name = "inspection_date", nullable = false)
    private LocalDate inspectionDate;  // 실사 일자

    @Column(name = "inspection_number", nullable = false)
    private Long inspectionNumber;  // 같은 날짜 내에서 고유한 전표 번호

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InspectionStatus status;  // '미조정', '조정 중', '조정 완료'

    @Column(name = "comment")
    private String comment;  // 비고
}
