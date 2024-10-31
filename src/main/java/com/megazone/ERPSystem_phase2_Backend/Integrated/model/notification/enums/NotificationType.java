package com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums;

public enum NotificationType {
    NEW_VOUCHER("신규 전표"),
    APPROVAL_VOUCHER("전표 승인"),
    REJECT_VOUCHER("전표 반려"),
    CHANGE_PERMISSION("권한 변경"),
    NEW_CASHMEMO("현금적요 추가"),
    NEW_TRANSFERMEMO("대체적요 추가"),
    NEW_ACCOUNTSUBJECT("계정과목 추가"),
    UPDATE_ACCOUNTSUBJECT("계정과목 수정"),
    DELETE_ACCOUNTSUBJECT("계정과목 삭제"),
    NEW_BANKACCOUNT("신규 계좌등록"),
    UPDATE_BANKACCOUNT("계좌정보 수정"),
    NEW_CLIENT("신규 거래처 추가"),
    UPDATE_CLIENT("거래처 수정"),
    NEW_COMPANY("신규 회사 추가"),
    UPDATE_COMPANY("회사 수정"),
    NEW_CREDITCARD("신용카드 추가"),
    DELETE_RESOLVEDVOUCHER("승인 일반전표 삭제"),
    NEW_UNRESOLVEDVOUCHER("미결일반전표 추가"),
    DELETE_UNRESOLVEDVOUCHER("미결일반전표 삭제"),
    JOURNAL_ENTRY_TYPESET("분개유형 수정"),
    DELETE_RESOLVED_SALEANDPURCHASE_VOUCHER("승인 매출매입전표 삭제"),
    NEW_UNRESOLVED_SALEANDPURCHASE_VOUCHER("미결 매출매입전표 추가"),
    NEW_MAINTENANCE_HISTORY("신규 유지보수"),
    UPDATE_MAINTENANCE_HISTORY("유지보수 변경"),
    NEW_EQUIPMENT_DATA("신규 설비"),
    UPDATE_EQUIPMENT_DATA("설비 변경"),
    NEW_MATERIAL("신규 자재"),
    UPDATE_MATERIAL("자재 변경"),
    UPDATE_WORKER("작업자 교육이수여부 변경"),


    UPDATE_EMPLOYEE("사원 정보 변경"),
    CREATE_EMPLOYEE("사원 등록"),
    CREATE_TRANSFER("발령기록 등록"),
    UPDATE_TRANSFER("발령기록 수정"),
    CREATE_DEPARTMENT("부서 등록");
    private final String koreanName;

    NotificationType(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }
}