package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountSubjectsAndMemosDTO {
    private List<StructureDTO> structures; // 계정과목 체계 목록
    private List<AccountSubjectDTO> accountSubjects; // 계정과목 목록
    private AccountSubjectDetailDTO accountSubjectDetail; // 계정과목 상세 정보
}