package com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment;

//유지보수 관리 이력

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                             //pk

    private String maintenanceManager;          //유지보수 관리자

    private LocalDate maintenanceDate;          //유지보수 날짜

    private BigDecimal maintenanceCost;         //유지보수에 소요된 비용

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equimentData_id")
    private EquipmentData equipment;      //유지보수 관리하는 설비정보 테이블 참조.(설비명)

}
