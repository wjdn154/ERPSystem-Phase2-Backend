
package com.megazone.ERPSystem_phase2_Backend.logistics.model.purchase_management;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 구매 전표 테이블
 * 구매 전표 대한 정보가 있는 테이블
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseInvoice {

    // 고유 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 구매서와의 일대일 관계 설정
    @OneToOne(mappedBy = "purchaseInvoice")
    private Purchase purchase;

}
