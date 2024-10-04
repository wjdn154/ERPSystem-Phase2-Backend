package com.megazone.ERPSystem_phase2_Backend.production.model.work_performance.goods_receipt.enums;

public enum SerialNoStatus {

    PRODUCTION ,     //생산  => 제품이 생산 완료된 상태
    SHIPPED,         //출고  => 제품이 고객에게 발송된 상태
    RETURNED,        //반품  => 고객이 제품을 반송한 상태
    DISCARDED,       //폐기  => 더 이상 유통할 수 없는 상태
    IN_STOCK,        //재고  => 제품이 창고에 안전하게 보관되고 있는 상태

}

/** 생산 : 제품이 생산 완료되었고, 품질검사를 통과한 상태.
 * 이 단계에서는 제품이 제조 공정을 마치고, 출고를 준비할 수 있는 상태임.
 * 이 상태의 제품은 시리얼 번호가 부여될 수 있음.
 *
 * 재고 : 제품이 창고에 안전하게 보관되고 있는 상태.
 * 이 상태에서는 제품이 판매를 위해 준비되었거나, 다른 작업을 기다리고 있는 상태일 수 있음.
 *
 * 반품 : 고객이 제품을 반송한 상태임. 고객이 제품에 대한 불만이나 결함을 발견했을 경우 발생할 수 있음.
 * 이 상태의 제품은 재검사를 통해 다시 재고로 들어가거나 폐기될 수 있음.
 *
 * 폐기 : 제품이 사용 불가능하거나 더 이상 유통할 수 없는 상태임.
 * 품질검사에서 불량으로 판별되었거나, 유통기한이 지나거나 손상된 제품 등이 해당됨.
 * 이 상태의 제품은 회사 내부에서 관리되며 재사용이나 재판매가 불가능함.
 * */
