package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.credit_card.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnershipDTO {
    private Long creditCardId; // 신용카드 ID
    private Long ownerId; // 소유자 (Employee 테이블 참조)
    private Long managerId; // 담당자 (Employee 테이블 참조)
}