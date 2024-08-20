package com.megazone.ERPSystem_phase2_Backend.production.model.outsourcing;

/*외주 실적 관리
    외주 업체에서 수행한 작업이 완료된 후, 그 결과를 기록하고 관리하는 과정임.
    외주 업체에서 납품된 제품의 수량, 품목, 납품 날짜 등 기록
    외주 발주와 연결하여 발주된 수량과 실적 수량을 비교
    남품된 제품이 모두 제대로 도착했는지 확인.
*/
//import com.megazone.ERPSystem_phase2_Backend.production.model.basic_information.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.MaterialData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutsourcingPerformance {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                              //pk

    @Column(nullable = false)
    private LocalDate performanceDate;                   //실적일 (납품된 날짜)

    @Column(nullable = false)
    private String sortation;                       //구분   무슨구분?

    @Column(nullable = false)
    private Boolean performanceClassification;       //실적 구분 (적합/비적합)

    @Column(nullable = false)
    private BigDecimal performanceQuantity;               //실적 수량 (납품된 수량)

    @Column(nullable = false)
    private String prosecutor;                      //검사(대기/합격/불합격)    enum

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "outsourcingOrders_id")
    private OutsourcingOrders outsourcingOrders;      //발주 식별자(발주 fk)

    @OneToMany(mappedBy = "outsourcingPerformance", fetch = FetchType.LAZY)
    private List<OutsourcingInspections> outsourcingInspections;      //외주 실적 검사    하나의 남품 실적이 여러번의 검사 결과를 가질 수 있음 (재검사)

    //자재 사용 유무
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "meterialData_id")
//    private MaterialData materialStatus;                //자재 현황

//    @ManyToOne(fetch = FetchType.LAZY , optional = false)
//    @JoinColumn(name = "process_id")
//    private ProcessDetails process;             //공정
//
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workcenter_id")
    private Workcenter workcenter;       //작업장
}
