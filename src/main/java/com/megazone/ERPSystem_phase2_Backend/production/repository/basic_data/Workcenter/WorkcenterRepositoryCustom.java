package com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.Workcenter;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.enums.WorkcenterType;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.WorkerAssignment;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkcenterRepositoryCustom {

    Optional<Workcenter> findByCode(String code);

    /**
     * 이름의 일부 또는 정확한 이름으로 작업장 목록 조회 (부분 일치)
     * @param name 작업장명
     * @return 해당 이름을 가진 Workcenter 리스트
     */
    List<Workcenter> findByNameContaining(String name);

    /**
     * 코드의 일부 또는 정확한 코드로 작업장 목록 조회 (부분 일치)
     * @param code 작업장 코드
     * @return 해당 코드를 가진 Workcenter 리스트
     */
    List<Workcenter> findByCodeContaining(String code);

    /**
     * 사용 중인 작업장 조회 메서드
     * @param isActive 작업장 사용 여부
     * @return 사용 여부에 따른 Workcenter 리스트
     */
    List<Workcenter> findByActive(boolean isActive);

    /**
     * 공장명으로 소속 작업장 조회 메서드
     * @param factoryName 물류 창고 엔티티의 공장명
     * @return 입력한 공장명이 포함된 Workcenter 리스트
     */
    List<Workcenter> findByFactoryNameContaining(String factoryName);

    /**
     * 공장 지정코드로 소속 작업장 조회 메서드
     * @param factoryCode 물류 창고 엔티티의 공장(창고) 코드
     * @return 입력한 공장코드가 포함된 Workcenter 리스트
     */
    List<Workcenter> findByFactoryCodeContaining(String factoryCode);

    /**
     * 생산공정명이 포함된 작업장 조회 메서드
     * @param ProcessName ProcessDetails 생산공정 엔티티의 생산공정명
     * @return 입력한 생산공정명이 포함된 Workcenter 리스트
     */
    List<Workcenter> findByProcessNameContaining(String ProcessName);

    /**
     * 생산공정코드가 포함된 작업장 조회 메서드
     * @param ProcessCode
     * @return 입력한 생산공정코드가 포함된 Workcenter 리스트
     */
    List<Workcenter> findByProcessCodeContaining(String ProcessCode);

    /**
     * 작업장 유형으로 해당 작업장 조회 메서드
     * @param types enum 작업장 유형
     * @return 선택한 작업장 유형에 해당하는 Workcenter 리스트
     */
    List<Workcenter> findByTypeIn(List<WorkcenterType> types);

    /**
     * 설비 이름으로 작업장 목록 조회 (부분 일치)
     * @param equipmentName 설비명 (EquipmentData 엔티티)
     * @return 입력한 설비명을 가진 Workcenter 리스트
     */
    List<Workcenter> findByEquipmentNameContaining(String equipmentName);

    /**
     * 설비 모델 번호로 작업장 목록 조회 (부분 일치)
     * @param equipmentModelNumber 설비 모델 번호 (EquipmentData 엔티티)
     * @return 입력한 설비 모델 번호를 가진 Workcenter 리스트
     */
    List<Workcenter> findByEquipmentModelNumberContaining(String equipmentModelNumber);

    /**
     * ID로 작업장 세부 정보 조회 메서드
     * @param id 작업장 ID
     * @return 해당 ID를 가진 Workcenter 객체
     */
    Workcenter findWorkcenterDetailsById(Long id);

    /**
     * 특정 작업장의 오늘 작업자 목록 조회 메서드
     * @param workcenterId 작업장 ID
     * @param today 오늘 날짜
     * @return 오늘 날짜에 해당 작업장에 배치된 WorkerAssignment 리스트
     */
    List<WorkerAssignment> findTodayWorkerAssignmentsByWorkcenterId(Long workcenterId, LocalDate today);

    /**
     * 특정 작업장의 작업자 배치 이력 조회 메서드
     * @param workcenterId 작업장 ID
     * @return 해당 작업장의 모든 작업자 배치 이력 (WorkerAssignment 리스트)
     */
    List<WorkerAssignment> findWorkerAssignmentsByWorkcenterId(Long workcenterId);
}
