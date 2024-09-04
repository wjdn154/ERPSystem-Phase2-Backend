package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.Nature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NatureDTO {
    private Long id; // ID
    private String code; // 성격 코드
    private String name; // 성격명
}