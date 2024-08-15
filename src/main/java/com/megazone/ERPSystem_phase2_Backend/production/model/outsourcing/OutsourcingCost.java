package com.megazone.ERPSystem_phase2_Backend.production.model.outsourcing;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 외주단가 관련 정보를 저장하는 엔티티 클래스
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutsourcingCost {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // PK

    @Column(nullable = false)
    private BigDecimal standardCost;     // 표준원가(계획 및 예산 수립에 사용되며, 실제 원가와 비교하여 성과를 평가하는 기준, 제품가격결정)

    @Column(nullable = false)
    private BigDecimal actualCost;     // 실제원가(실제 생산 활동에서 발생한 실제 비용)

    @Column(nullable = false)
    private BigDecimal outsourcingCost;     // 외주단가(특정 작업이나 생산 공정을 외부 공급업체에 위탁할 때 지불하는 비용)

    @Column(nullable = false)
    private Double rate;     // Double 단가적용비율 : (외주단가 / 표준원가) * 100 or (실제원가 / 표준원가) * 100

//    @PrePersist @PreUpdate
//    private void calculateCostRate() {
//        if (standardCost != null && standardCost.compareTo(BigDecimal.ZERO) != 0)
//            // 외주단가 기준 단가적용비율 계산
//            rate = (outsourcingCost.divide(standardCost, 2, BigDecimal.ROUND_HALF_UP)).doubleValue() = 100;
//        else
//            rate = 0.0;
//    }

    @Column(nullable = false)
    private LocalDate startDate;     // 시작일 (계약기간, 외주작업유효기간)

    @Column(nullable = false)
    private LocalDate endDate;     // 종료일 (계약기간, 외주작업유효기간)

    @Column(nullable = false)
    private Boolean isUsed; // 사용 여부

//    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
//    @Column(nullable = false)
//    private Item item; // 품목 ( 품목군(폼목군코드, 품목군명) , 품번, 품명, 규격, 단위 )

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(nullable = false)
//    private Vendor supplier;     // 외주처 ( 거래처 ) == 외주 작업을 수행하는 공급업체(supplier) from 회계
}
