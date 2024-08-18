package com.megazone.ERPSystem_phase2_Backend.production.model.outsourcing;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/*외주 발주
생산지시번호, 자재출고상태, 지시일, 납기일, 지시수량, 단가, 상태, 검사, 외주공장, 외주발주 상태
*/

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutsourcingOrders {

    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    //pk

    @Column(nullable = false)
    private LocalDate billingDate;            //청구일. 발주 날짜

    @Column
    private LocalDate completionDate;         //완료날짜 (외주 발주 청구가 완료된 날짜)

    @Column(nullable = false)
    private LocalDate dueDate;                 //납품 예정일

    @Column
    private BigDecimal RefinedQuantity;         //정미수량
    
    @Column
    private Double loss;                    //Loss(%);
    
    @Column(nullable = false)
    private BigDecimal fixedQuantity;           //발주 수량

    @Column(nullable = false)
    private Boolean sortation;             //유상/무상 구분
    
    @Column(nullable = false)
    private String status;                 //발주 상태 (발주 등록/완료/취소)  enum

    @Column
    private String remarks;                  //비고

    @OneToMany(mappedBy = "outsourcingOrders", fetch = FetchType.LAZY )
    private List<OutsourcingPerformance> outsourcingPerformance;     //외주 실적 관리 (fk)   하나의 발주가 여러번의 납품 실적을 가질 수 있음.

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name = "outsourcingCost_id")
    private OutsourcingCost outsourcingCost;     //외주 단가


//    @ManyToOne(fetch = FetchType.LAZY)
//    @Column(nullable = false)
//    private Item item; // 품목 ( 품목군(폼목군코드, 품목군명) , 품번, 품명, 규격, 단위 )

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(nullable = false)
//    private Vendor supplier;     // 외주처 ( 거래처 ) == 외주 작업을 수행하는 공급업체(supplier) from 회계
}
