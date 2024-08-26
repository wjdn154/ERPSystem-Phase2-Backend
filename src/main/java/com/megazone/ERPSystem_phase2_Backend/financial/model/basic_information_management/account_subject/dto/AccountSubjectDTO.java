package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.AccountSubject;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.Nature;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.enums.EntryType;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.enums.IncreaseDecreaseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountSubjectDTO {
    private Long id; // 계정과목 ID (저장할 때는 필요 없지만, 조회 및 반환 시 필요)
    private String structureCode; // 계정체계 코드
    private String parentCode; // 부모 계정과목 코드
    private String code; // 계정과목 코드
    private String name; // 계정과목 이름
    private String englishName; // 영문명
    private String natureCode; // 성격 코드
    private String natureName; // 성격명
    private String standardFinancialStatementCode; // 표준재무제표 코드
    private EntryType entryType; // 차대구분
    private IncreaseDecreaseType increaseDecreaseType; // 증감구분
    private Boolean isActive; // 활성화 여부
    private Boolean modificationType; // 수정 가능 여부
    private Boolean isForeignCurrency; // 외화 사용 여부
    private Boolean isBusinessCar; // 업무용 차량 여부
    private List<CashMemoDTO> cashMemos; // 현금적요 목록
    private List<TransferMemoDTO> transferMemos; // 대체적요 목록
    private List<FixedMemoDTO> fixedMemos; // 고정적요 목록
}