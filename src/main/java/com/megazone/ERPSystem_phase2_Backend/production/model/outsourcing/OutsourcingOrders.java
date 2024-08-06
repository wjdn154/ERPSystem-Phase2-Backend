package com.megazone.ERPSystem_phase2_Backend.production.model.outsourcing;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/*외주 발주
생산지시번호, 자재출고상태, 지시일, 납기일, 지시수량, 단가, 상태, 검사, 외주공장, 외주발주 상태
*/

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutsourcingOrders {

    @Id  @GeneratedValue
    private Long id;

    @Column
    private Date date;                   //청구일
    @Column
    private String itemNumber;           //품번
    @Column
    private String itemName;              //품명
    @Column
    private String unit;                  //단위
    @Column
    private Long RefinedQuantity;         //정미수량
    @Column
    private Long loss;                    //Loss(%);
    @Column
    private Long fixedQuantity;           //확정 수량
    @Column
    private String sortation;             //유상/무상 구분
    @Column
    private Long price;                   //금액
    @Column
    private String note;                  //비고

    private String status;                 //상태 (등록/확정/반려)


    //외주단가 참조
}
