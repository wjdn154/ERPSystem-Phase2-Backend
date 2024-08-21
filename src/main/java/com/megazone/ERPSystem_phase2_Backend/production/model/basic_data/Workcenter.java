package com.megazone.ERPSystem_phase2_Backend.production.model.basic_data;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.enums.WorkcenterType;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.WorkerAssignment;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.EquipmentData;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProcessDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 작업장 정보 테이블
 */

@Entity(name="workcenter")
@Table(name = "basic_data_workcenter",
        indexes = {
                @Index(name = "idx_workcenter_code", columnList = "code")
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Workcenter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // 고유식별자

    @Column(nullable = false, unique = true)
    private String code; // 작업장코드 (식별자와 별도의 지정코드)

    @Enumerated(EnumType.STRING)
    private WorkcenterType workcenterType;

    @Column(nullable = false)
    private String name; // 작업장명

    @Column(nullable = true)
    private String description; // 작업장 설명

    @Column(nullable = false)
    private Boolean isActive; // 사용 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private Warehouse factory;  // 공장 엔티티 from 물류 창고관리의 공장

    @OneToMany(mappedBy = "location")
    private List<EquipmentData> equipmentList = new ArrayList<>(); // 설비 목록

    @OneToMany(mappedBy = "workcenter")
    private List<WorkerAssignment> workerAssignments = new ArrayList<>(); // 작업자 배치 이력

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_id")
    private ProcessDetails processDetails; // 작업장에서 이뤄지는 생산공정

    // 작업지시 onetomany (mappedby = "workorders")
    // 작업자배치이력에서 연결되면 없어도됨

}
