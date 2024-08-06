package com.megazone.ERPSystem_phase2_Backend.logistics.model.sales_management.quotation_registrarion;

import jakarta.persistence.*;
import lombok.*;

/**
 * 견적서 엔티티
 * 견적서에 대한 정보가 있는 엔티티
 */

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Quotation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
}