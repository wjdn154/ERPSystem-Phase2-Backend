package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment;

//유지보수 관리 이력

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.enums.MaintenanceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "equipment_maintenanceHistory")
@Table(name = "equipment_maintenanceHistory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                               //pk

    @Column(nullable = false)
    private String maintenanceManager;             //유지보수 관리자. 작업자 테이블 참조?

    @Column(nullable = false)
    private LocalDate maintenanceDate;              //유지보수 날짜

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaintenanceType maintenanceType;        //유지보수 종류(유형). 정기점검, 고장 수리, 긴급 수리
    
    @Column(nullable = false)
    private String title;                            //유지보수 작업의 제목

    @Column(nullable = false)
    private String maintenanceDetail;               //유지보수 작업의 상세 내용

    @Column(nullable = false)
    private BigDecimal maintenanceCost;             //유지보수에 소요된 비용

    @Column(nullable = false)
    private Boolean maintenanceStatus;             //유지보수 상태(완료, 작업중)

    @Column(nullable = false)
    private LocalDate nextMaintenanceDate;          //다음 유지보수 예정일

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equimentData_id")
    private EquipmentData equipment;      //유지보수 관리하는 설비정보 테이블 참조.(설비명, 작업장이름, 공장이름)

}
