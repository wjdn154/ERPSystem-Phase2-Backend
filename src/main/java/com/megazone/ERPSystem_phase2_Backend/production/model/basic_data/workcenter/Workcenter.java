package com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.workcenter;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.Company;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.workcenter.enums.WorkcenterType;
import com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling.WorkerAssignment;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.EquipmentData;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing.ProcessDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

/**
 작업장은 CRUD 작업을 관리하고, 연관된 생산공정, 소속 공장, 설비와의 연관 관계를 처리합니다.
 작업장은 연관된 엔티티(생산공정, 공장, 설비)를 CRUD할 수 있고,
 작업장 자체에서 배정된 작업자 명단을 조회하지만, 작업자 배정 관련 로직은 직접 수정하지 않습니다.
 */

@Entity(name="basic_data_workcenter")
@Table(name = "basic_data_workcenter",
        indexes = {
                @Index(name = "idx_workcenter_code", columnList = "code")
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @JoinColumn(name = "warehouse_id", nullable = true)
    private Warehouse factory;  // 공장 엔티티 from 물류 창고관리의 공장

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "workcenter")
    @BatchSize(size = 10) // 컬렉션을 한 번에 최대 10개 로딩
    @Builder.Default @Fetch(FetchMode.SUBSELECT)
    private List<EquipmentData> equipmentList = new ArrayList<>(); // 설비 목록

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "workcenter")
    @BatchSize(size = 10) // 컬렉션을 한 번에 최대 10개 로딩
    @Builder.Default @Fetch(FetchMode.SUBSELECT)
    private List<WorkerAssignment> workerAssignments = new ArrayList<>(); // 작업자 배치 이력

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_id")
    private ProcessDetails processDetails; // 작업장에서 이뤄지는 생산공정

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id")
    private Company company;                            //회사 테이블 참조 (회사 아이디)

}