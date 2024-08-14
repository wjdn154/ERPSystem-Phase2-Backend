package com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.enums;

// 고용 유형

public enum EmploymentType {
    FULL_TIME, // 정규직 (주 40시간 이상 근무하는 정규직 근로자 )
    CONTRACT, // 계약직 (일정 계약 기간 동안 고용되는 계약직 근로자 )
    PART_TIME, // 파트타임 ( 주 40시간 미만으로 근무하는 파트타임 근로자 )
    TEMPORARY, // 임시직 ( 특정 기간 동안만 고용되는 임시직 근로자 )
    INTERN, //인턴 ( 교육 목적으로 단기적으로 고용되는 인턴 근로자 )
    CASUAL, // 일용직 ( 비정기적으로 고용되는 일용직 근로자 )
    FREELANCE // 프리랜서 ( 특정 프로젝트나 작업을 위해 고용되는 프리랜서 )
}
