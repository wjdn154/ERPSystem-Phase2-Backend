package com.megazone.ERPSystem_phase2_Backend.production.model.outsourcing;

/*외주 실적 검사
    외주 업체에서 남품한 제품이나 부품이 품질 기준을 충족하는지 여부를 검토하는 과정.
    외주 업체에서 남품된 제품의 품질을 확인하고, 불량품이나 규격에 맞지 않는 제품을 걸러내는 것.
        납품된 제품에 대한 품질 검사 수행
        검사 결과를 기록하고, 불합격된 제품에 대해 재작업 또는 반품 등의 조치를 결정
        검사 결과에 따라 최종적으로 납품 실적을 확정.
*/

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutsourcingInspections {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                          //pk
    
    @Column(nullable = false)
    private Date inspectionDate;             //검사 날짜

    @Column(nullable = false)
    private String inspectionResult;         //검사 결과(합격/불합격)   boolean?true?false?

    @Column(nullable = false)
    private Long defectiveQuantity;        //불량수량 (검사결과 후 불합격된 제품 수량)

    @Column(nullable = false)
    private String inspector;                //검사 수행자

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "outsourcingPerformance_id")
    private OutsourcingPerformance outsourcingPerformance;     //실적관리 fk .
    
}
