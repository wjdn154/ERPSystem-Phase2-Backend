package com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.enums.UserPermission;



// 권한 엔티티

@Data
@Entity(name="users_permission")
@Table(name="users_permission")
@NoArgsConstructor
@AllArgsConstructor
public class Permission  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 회사관리 권한
    @Enumerated(EnumType.STRING) private UserPermission adminPermission = UserPermission.NO_ACCESS;  // 관리자 권한

    // 재무회계 권한
    // 기초정보관리 관련 권한
    @Enumerated(EnumType.STRING) private UserPermission clientRegistrationPermission = UserPermission.NO_ACCESS;  // 거래처등록 권한
    @Enumerated(EnumType.STRING) private UserPermission accountSubjectPermission = UserPermission.NO_ACCESS;  // 계정과목 및 적요 등록 권한
    @Enumerated(EnumType.STRING) private UserPermission environmentPermission = UserPermission.NO_ACCESS;  // 환경등록 권한
    // 전표관리 관련 권한
    @Enumerated(EnumType.STRING) private UserPermission generalVoucherPermission = UserPermission.NO_ACCESS;  // 일반전표입력 권한
    @Enumerated(EnumType.STRING) private UserPermission salesPurchaseVoucherPermission = UserPermission.NO_ACCESS;  // 매입매출전표입력 권한
    @Enumerated(EnumType.STRING) private UserPermission electronicTaxPermission = UserPermission.NO_ACCESS;  // 전자세금계산서발행 권한
    // 장부관리 관련 권한
    @Enumerated(EnumType.STRING) private UserPermission clientLedgerPermission = UserPermission.NO_ACCESS;  // 거래처원장 권한
    @Enumerated(EnumType.STRING) private UserPermission clientAccountLedgerPermission = UserPermission.NO_ACCESS;  // 거래처별계정과목별원장 권한
    @Enumerated(EnumType.STRING) private UserPermission accountLedgerPermission = UserPermission.NO_ACCESS;  // 계정별원장 권한
    @Enumerated(EnumType.STRING) private UserPermission cashBookPermission = UserPermission.NO_ACCESS;  // 현금출납장 권한
    @Enumerated(EnumType.STRING) private UserPermission dailyMonthlyPermission = UserPermission.NO_ACCESS;  // 일계표(월계표) 권한
    @Enumerated(EnumType.STRING) private UserPermission journalPermission = UserPermission.NO_ACCESS;  // 분개장 권한
    @Enumerated(EnumType.STRING) private UserPermission generalLedgerPermission = UserPermission.NO_ACCESS;  // 총계정원장 권한
    @Enumerated(EnumType.STRING) private UserPermission salesPurchaseLedgerPermission = UserPermission.NO_ACCESS;  // 매입매출장 권한
    @Enumerated(EnumType.STRING) private UserPermission taxInvoicePermission = UserPermission.NO_ACCESS;  // 세금계산서(계산서)현황 권한
    @Enumerated(EnumType.STRING) private UserPermission voucherPrintPermission = UserPermission.NO_ACCESS;  // 전표출력 권한
    // 결산/재무제표관리 관련 권한
    @Enumerated(EnumType.STRING) private UserPermission closingDataPermission = UserPermission.NO_ACCESS;  // 결산자료입력 권한
    @Enumerated(EnumType.STRING) private UserPermission trialBalancePermission = UserPermission.NO_ACCESS;  // 합계잔액시산표 권한
    @Enumerated(EnumType.STRING) private UserPermission financialPositionPermission = UserPermission.NO_ACCESS;  // 재무상태표 권한
    @Enumerated(EnumType.STRING) private UserPermission incomeStatementPermission = UserPermission.NO_ACCESS;  // 손익계산서 권한
    @Enumerated(EnumType.STRING) private UserPermission costStatementPermission = UserPermission.NO_ACCESS;  // 제조원가명세서 권한
    @Enumerated(EnumType.STRING) private UserPermission profitDistributionPermission = UserPermission.NO_ACCESS;  // 이익잉여금처분계산서 권한
    @Enumerated(EnumType.STRING) private UserPermission cashFlowPermission = UserPermission.NO_ACCESS;  // 현금흐름표 권한
    @Enumerated(EnumType.STRING) private UserPermission equityChangesPermission = UserPermission.NO_ACCESS;  // 자본변동표 권한
    @Enumerated(EnumType.STRING) private UserPermission closingAnnexPermission = UserPermission.NO_ACCESS;  // 결산부속명세서 권한
    // 전기분재무제표 관련 권한
    @Enumerated(EnumType.STRING) private UserPermission previousFinancialPositionPermission = UserPermission.NO_ACCESS;  // 전기분재무상태표 권한
    @Enumerated(EnumType.STRING) private UserPermission previousIncomeStatementPermission = UserPermission.NO_ACCESS;  // 전기분손익계산서 권한
    @Enumerated(EnumType.STRING) private UserPermission previousCostStatementPermission = UserPermission.NO_ACCESS;  // 전기분원가명세서 권한
    @Enumerated(EnumType.STRING) private UserPermission previousProfitDistributionPermission = UserPermission.NO_ACCESS;  // 전기분잉여금처분계산서 권한
    @Enumerated(EnumType.STRING) private UserPermission clientInitialPermission = UserPermission.NO_ACCESS;  // 거래처별초기이월 권한
    @Enumerated(EnumType.STRING) private UserPermission closingCarryoverPermission = UserPermission.NO_ACCESS;  // 마감후이월 권한
    // 고정자산관리 관련 권한
    @Enumerated(EnumType.STRING) private UserPermission fixedAssetRegisterPermission = UserPermission.NO_ACCESS;  // 고정자산등록 권한
    @Enumerated(EnumType.STRING) private UserPermission undepreciatedPermission = UserPermission.NO_ACCESS;  // 미상각분감가상각비 권한
    @Enumerated(EnumType.STRING) private UserPermission transferredDepreciationPermission = UserPermission.NO_ACCESS;  // 양도자산감가상각비 권한
    @Enumerated(EnumType.STRING) private UserPermission registerBookPermission = UserPermission.NO_ACCESS;  // 고정자산관리대장 권한
    // 자금관리 관련 권한
    @Enumerated(EnumType.STRING) private UserPermission billsReceivablePermission = UserPermission.NO_ACCESS;  // 받을어음현황 권한
    @Enumerated(EnumType.STRING) private UserPermission billsPayablePermission = UserPermission.NO_ACCESS;  // 지급어음현황 권한
    @Enumerated(EnumType.STRING) private UserPermission dailyFinancePermission = UserPermission.NO_ACCESS;  // 일일자금명세(경리일보) 권한
    @Enumerated(EnumType.STRING) private UserPermission depositsStatusPermission = UserPermission.NO_ACCESS;  // 예적금현황 권한


    // 인사관리 권한
    // 기초정보관리 관련 권한
    @Enumerated(EnumType.STRING) private UserPermission employeeManagementPermission = UserPermission.NO_ACCESS;  // 사원 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission userManagementPermission = UserPermission.NO_ACCESS;  // 사용자 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission departmentManagementPermission = UserPermission.NO_ACCESS;  // 부서 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission assignmentManagementPermission = UserPermission.NO_ACCESS;  // 발령 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission performanceEvaluationPermission = UserPermission.NO_ACCESS;  // 성과 평가 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission retirementManagementPermission = UserPermission.NO_ACCESS;  // 퇴사자 관리 권한
    // 출결관리 관련 권한
    @Enumerated(EnumType.STRING) private UserPermission timeManagementPermission = UserPermission.NO_ACCESS;  // 근태 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission leaveManagementPermission = UserPermission.NO_ACCESS;  // 휴가 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission overtimeManagementPermission = UserPermission.NO_ACCESS;  // 초과근무 관리 권한
    // 채용관리 관련 권한
    @Enumerated(EnumType.STRING) private UserPermission jobPostingsPermission = UserPermission.NO_ACCESS;  // 채용 공고 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission applicantManagementPermission = UserPermission.NO_ACCESS;  // 지원자 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission applicationManagementPermission = UserPermission.NO_ACCESS;  // 지원서 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission interviewManagementPermission = UserPermission.NO_ACCESS;  // 인터뷰 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission jobOffersPermission = UserPermission.NO_ACCESS;  // 채용 제안 관리 권한


    // 물류관리 권한
    // 기초정보관리 관련 권한
    @Enumerated(EnumType.STRING) private UserPermission itemManagementPermission = UserPermission.NO_ACCESS;  // 품목 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission itemGroupManagementPermission = UserPermission.NO_ACCESS;  // 품목 그룹 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission warehouseRegistrationPermission = UserPermission.NO_ACCESS;  // 창고등록 권한
    // 영업관리 관련 권한
    @Enumerated(EnumType.STRING) private UserPermission quotationPermission = UserPermission.NO_ACCESS;  // 견적서 권한
    @Enumerated(EnumType.STRING) private UserPermission orderPermission = UserPermission.NO_ACCESS;  // 주문서 권한
    @Enumerated(EnumType.STRING) private UserPermission salePermission = UserPermission.NO_ACCESS;  // 판매 권한
    @Enumerated(EnumType.STRING) private UserPermission shippingOrderPermission = UserPermission.NO_ACCESS;  // 출하지시서 권한
    @Enumerated(EnumType.STRING) private UserPermission shipmentPermission = UserPermission.NO_ACCESS;  // 출하 권한
    // 구매관리 관련 권한
    @Enumerated(EnumType.STRING) private UserPermission purchaseRequestPermission = UserPermission.NO_ACCESS;  // 발주 요청 권한
    @Enumerated(EnumType.STRING) private UserPermission purchasePlanPermission = UserPermission.NO_ACCESS;  // 발주 계획 권한
    @Enumerated(EnumType.STRING) private UserPermission priceRequestPermission = UserPermission.NO_ACCESS;  // 단가 요청 권한
    @Enumerated(EnumType.STRING) private UserPermission purchaseOrderPermission = UserPermission.NO_ACCESS;  // 발주서 권한
    @Enumerated(EnumType.STRING) private UserPermission purchasePermission = UserPermission.NO_ACCESS;  // 구매 권한
    @Enumerated(EnumType.STRING) private UserPermission inboundOrderPermission = UserPermission.NO_ACCESS;  // 입고지시서 권한
    // 반품관리 관련 권한
    @Enumerated(EnumType.STRING) private UserPermission returnsReceptionPermission = UserPermission.NO_ACCESS;  // 반품 접수 권한
    @Enumerated(EnumType.STRING) private UserPermission returnsStatusPermission = UserPermission.NO_ACCESS;  // 반품 현황 권한
    // 출하지시서 관련 권한
    @Enumerated(EnumType.STRING) private UserPermission shippingOrderViewPermission = UserPermission.NO_ACCESS;  // 출하지시서조회 권한
    @Enumerated(EnumType.STRING) private UserPermission shippingOrderInputPermission = UserPermission.NO_ACCESS;  // 출하지시서입력 권한
    // 출하관리 관련 권한
    @Enumerated(EnumType.STRING) private UserPermission shipmentViewPermission = UserPermission.NO_ACCESS;  // 출하조회 권한
    @Enumerated(EnumType.STRING) private UserPermission shipmentInputPermission = UserPermission.NO_ACCESS;  // 출하입력 권한
    @Enumerated(EnumType.STRING) private UserPermission shipmentStatusPermission = UserPermission.NO_ACCESS;  // 출하현황 권한
    // 입고관리 관련 권한
    @Enumerated(EnumType.STRING) private UserPermission inboundExpectedPermission = UserPermission.NO_ACCESS;  // 입고예정 권한
    @Enumerated(EnumType.STRING) private UserPermission inboundProcessingPermission = UserPermission.NO_ACCESS;  // 입고처리 권한
    // 출고관리 관련 권한
    @Enumerated(EnumType.STRING) private UserPermission outboundExpectedPermission = UserPermission.NO_ACCESS;  // 출고예정 권한
    @Enumerated(EnumType.STRING) private UserPermission outboundExpectedStatusPermission = UserPermission.NO_ACCESS;  // 출고예정현황 권한
    @Enumerated(EnumType.STRING) private UserPermission outboundProcessingPermission = UserPermission.NO_ACCESS;  // 출고처리 권한
    // 재고관리 관련 권한
    @Enumerated(EnumType.STRING) private UserPermission inventoryAdjustmentStepsPermission = UserPermission.NO_ACCESS;  // 재고조정진행단계 권한
    @Enumerated(EnumType.STRING) private UserPermission inventoryInspectionViewPermission = UserPermission.NO_ACCESS;  // 재고실사조회 권한
    @Enumerated(EnumType.STRING) private UserPermission inventoryInspectionStatusPermission = UserPermission.NO_ACCESS;  // 재고실사현황 권한
    @Enumerated(EnumType.STRING) private UserPermission inventoryAdjustmentStatusPermission = UserPermission.NO_ACCESS;  // 재고조정현황 권한



    // 생산관리 권한
    // 기초정보관리 - 작업장관리 관련 권한
    @Enumerated(EnumType.STRING) private UserPermission workcenterManagementPermission = UserPermission.NO_ACCESS;  // 작업장 관리 권한
    // 기초정보관리 - 공정관리 관련 권한
    @Enumerated(EnumType.STRING) private UserPermission processDetailsPermission = UserPermission.NO_ACCESS;  // 공정세부정보 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission routingManagementPermission = UserPermission.NO_ACCESS;  // Routing 관리 권한
    // 기초정보관리 - 자재명세서관리 관련 권한
    @Enumerated(EnumType.STRING) private UserPermission bomManagementPermission = UserPermission.NO_ACCESS;  // BOM 관리 권한

    // 자원관리 관련 권한
    @Enumerated(EnumType.STRING) private UserPermission workerManagementPermission = UserPermission.NO_ACCESS;  // 작업자 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission materialManagementPermission = UserPermission.NO_ACCESS;  // 자재 정보 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission equipmentManagementPermission = UserPermission.NO_ACCESS;  // 설비 정보 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission maintenanceHistoryPermission = UserPermission.NO_ACCESS;  // 유지보수 이력 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission wasteManagementPermission = UserPermission.NO_ACCESS;  // 폐기물 관리 권한

    // 생산운영 및 계획관리 관련 권한
    @Enumerated(EnumType.STRING) private UserPermission mpsPermission = UserPermission.NO_ACCESS;  // 주생산 계획 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission mrpPermission = UserPermission.NO_ACCESS;  // 자재소요량 계획 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission materialInputStatusPermission = UserPermission.NO_ACCESS;  // 실자재 투입 현황 권한
    @Enumerated(EnumType.STRING) private UserPermission productionRequestPermission = UserPermission.NO_ACCESS;  // 생산 의뢰 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission planOfMakeToOrderPermission = UserPermission.NO_ACCESS;  // 주문 생산 계획 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission planOfMakeToStockPermission = UserPermission.NO_ACCESS;  // 재고 생산 계획 관리 권한

    // 작업지시 관리 관련 권한
    @Enumerated(EnumType.STRING) private UserPermission shiftTypePermission = UserPermission.NO_ACCESS;  // 교대유형 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission productionOrderPermission = UserPermission.NO_ACCESS;  // 작업 지시 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission workerAssignmentPermission = UserPermission.NO_ACCESS;  // 작업자 배정이력 관리 권한

    // 생산실적관리 관련 권한
    @Enumerated(EnumType.STRING) private UserPermission workPerformancePermission = UserPermission.NO_ACCESS;  // 작업 실적 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission dailyReportPermission = UserPermission.NO_ACCESS;  // 생산 일보 처리 권한
    @Enumerated(EnumType.STRING) private UserPermission monthlyReportPermission = UserPermission.NO_ACCESS;  // 생산 월보 처리 권한

    // 품질관리 관련 권한
    @Enumerated(EnumType.STRING) private UserPermission defectGroupAndTypePermission = UserPermission.NO_ACCESS;  // 불량군/유형 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission qualityInspectionPermission = UserPermission.NO_ACCESS;  // 품질 검사 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission lotManagementPermission = UserPermission.NO_ACCESS;  // LOT 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission serialManagementPermission = UserPermission.NO_ACCESS;  // Serial No 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission goodsReceiptPermission = UserPermission.NO_ACCESS;  // 생산품 입고 처리 권한

    // 외주/계약관리 관련 권한
    @Enumerated(EnumType.STRING) private UserPermission outsourcingPricePermission = UserPermission.NO_ACCESS;  // 외주 단가 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission outsourcingOrderPermission = UserPermission.NO_ACCESS;  // 외주 발주 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission outsourcingInspectionPermission = UserPermission.NO_ACCESS;  // 외주 검사 관리 권한
    @Enumerated(EnumType.STRING) private UserPermission outsourcingPerformancePermission = UserPermission.NO_ACCESS;  // 외주 실적 관리 권한
}
