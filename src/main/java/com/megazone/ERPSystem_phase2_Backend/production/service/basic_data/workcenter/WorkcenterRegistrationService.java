package com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.workcenter;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_registration.Warehouse;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.dto.WorkcenterDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.enums.WorkcenterType;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.WorkerAssignment;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProcessDetails;

import java.time.LocalDate;
import java.util.List;

public interface WorkcenterRegistrationService {

    Workcenter updateWorkcenter(String code, WorkcenterDTO workcenterDTO);

    /**
     * ID로 작업장 삭제 메서드
     * @param id 삭제할 Workcenter ID
     */
    void deleteById(Long id);

    Workcenter save(Workcenter workcenter);
}
//    /**
//     * 이름으로 작업장 검색 메서드
//     * @param name 작업장명
//     * @return 해당 이름을 가진 Workcenter 리스트
//     */
//    List<Workcenter> findByNameContaining(String name);
//
//    /**
//     * 지정 code로 작업장 검색 메서드
//     * @param code 작업장 고유코드
//     * @return 해당 코드를 가진 Workcenter 객체
//     */
//    List<Workcenter> findByCodeContaining(String code);
//
//    /**
//     * 사용 여부로 작업장 검색 메서드
//     * @param isActive 작업장 사용 여부
//     * @return 사용 여부에 따른 Workcenter 리스트
//     */
//    List<Workcenter> findByActive(boolean isActive);
//
//    /**
//     * 작업장 저장 메서드
//     * @param workcenter 저장할 작업장 객체
//     * @return 저장된 Workcenter 객체
//     */
//    Workcenter save(Workcenter workcenter);
//
//    /**
//     * ID로 작업장 검색 메서드
//     * @param id 작업장 ID
//     * @return 해당 ID를 가진 Workcenter 객체
//     */
//    Workcenter findById(Long id);
//
//
//    /**
//     * 공장 엔티티로 작업장 검색 메서드
//     * @param factory 공장 엔티티
//     * @return 해당 공장과 연관된 Workcenter 리스트
//     */
//    List<Workcenter> findByFactory(Warehouse factory);
//
//    /**
//     * 공장명으로 작업장 검색 메서드
//     * @param factoryName 공장명
//     * @return 해당 공장명을 가진 Workcenter 리스트
//     */
//    List<Workcenter> findByFactoryNameContaining(String factoryName);
//
//    /**
//     * 공장코드로 작업장 검색 메서드
//     * @param factoryCode 공장코드
//     * @return 해당 공장코드를 가진 Workcenter 리스트
//     */
//    List<Workcenter> findByFactoryCodeContaining(String factoryCode);
//
//    /**
//     * 공정 엔티티로 작업장 검색 메서드
//     * @param processDetails 공정 엔티티
//     * @return 해당 공정과 연관된 Workcenter 리스트
//     */
//    List<Workcenter> findByProcess(ProcessDetails processDetails);
//
//    /**
//     * 생산공정명으로 작업장 검색 메서드
//     * @param processName 생산공정명
//     * @return 해당 생산공정명을 가진 Workcenter 리스트
//     */
//    List<Workcenter> findByProcessNameContaining(String processName);
//
//    /**
//     * 생산공정코드로 작업장 검색 메서드
//     * @param processCode 생산공정코드
//     * @return 해당 생산공정코드를 가진 Workcenter 리스트
//     */
//    List<Workcenter> findByProcessCodeContaining(String processCode);
//
//    /**
//     * 설비명으로 작업장 검색 메서드
//     * @param equipmentName 설비명
//     * @return 해당 설비명을 가진 Workcenter 리스트
//     */
//    List<Workcenter> findByEquipmentNameContaining(String equipmentName);
//
//    /**
//     * 작업장 유형으로 작업장 검색 메서드
//     * @param types 작업장 유형
//     * @return 선택한 작업장 유형에 해당하는 Workcenter 리스트
//     */
//    List<Workcenter> findByTypeIn(List<WorkcenterType> types);
//
//    /**
//     * 오늘 날짜 기준으로 작업장에 배치된 작업자 리스트 조회
//     * @param workcenterId 작업장 ID
//     * @param today 오늘 날짜
//     * @return 오늘 날짜 기준 작업장에 배치된 작업자 리스트
//     */
//    List<WorkerAssignment> findTodayWorkerAssignmentsByWorkcenterId(Long workcenterId, LocalDate today);
//
//    /**
//     * 작업장에 배치된 모든 작업자 이력 조회
//     * @param workcenterId 작업장 ID
//     * @return 작업장에 배치된 모든 작업자 이력
//     */
//    List<WorkerAssignment> findWorkerAssignmentsByWorkcenterId(Long workcenterId);
