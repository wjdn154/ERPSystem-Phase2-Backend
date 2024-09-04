package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.CashMemo;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.FixedMemo;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.Nature;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.TransferMemo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountSubjectDetailDTO {
    private String code; // 계정과목 코드
    private String name; // 계정과목 이름
    private String parentCode; // 부모 계정과목 코드
    private String parentName; // 부모 계정과목 이름
    private String englishName; // 영어 이름
    private Boolean isActive; // 활성화 여부
    private Boolean modificationType; // 수정 가능 여부
    private String structureCode; // 계정과목 체계 코드
    private String natureCode; // 계정과목 성격 코드
    private String natureName; // 계정과목 성격 이름
    private Nature nature; // 계정과목 성격
    private String standardFinancialStatementCode; // 표준 재무제표 코드
    private String standardFinancialStatementName; // 표준 재무제표 이름
    private List<StandardFinancialStatementDTO> standardFinancialStatement; // 표준 재무제표 리스트
    private Boolean isForeignCurrency; // 외화 사용 여부
    private Boolean isBusinessCar; // 업무용 차량 여부
    private List<CashMemoDTO> cashMemos; // 현금 적요 리스트
    private List<TransferMemoDTO> transferMemos; // 대체 적요 리스트
    private List<FixedMemoDTO> fixedMemos; // 고정 적요 리스트
    private List<NatureDTO> natures; // 성격 리스트
}