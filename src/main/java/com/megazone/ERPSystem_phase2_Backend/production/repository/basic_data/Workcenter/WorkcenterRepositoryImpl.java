package com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.Workcenter;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.QWarehouse;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.QWorkerAssignment;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.WorkerAssignment;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.QEquipmentData;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.QProcessDetails;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.QWorkcenter.workcenter;

@Repository
@RequiredArgsConstructor
public class WorkcenterRepositoryImpl implements WorkcenterRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Workcenter> findAllWithDetails() {
        QWarehouse warehouse = QWarehouse.warehouse;
        QProcessDetails processDetails = QProcessDetails.processDetails;
        QEquipmentData equipmentData = QEquipmentData.equipmentData;

        return queryFactory.selectFrom(workcenter)
                .leftJoin(workcenter.factory, warehouse).fetchJoin()                  // 공장명 가져오기
                .leftJoin(workcenter.processDetails, processDetails).fetchJoin()       // 생산 공정명 가져오기
//                .leftJoin(workcenter.equipmentList, equipmentData).fetchJoin()         // 설비명 가져오기
//                .leftJoin(workcenter.workerAssignments, workerAssignment).fetchJoin()  // 작업자 배정 이력 가져오기
                .fetch();
    }

    @Override
    public List<Workcenter> findByFactoryNameContaining(String factoryName) {
        QWarehouse factory = QWarehouse.warehouse;
        return queryFactory
                .selectFrom(workcenter)
                .where(workcenter.factory.name.containsIgnoreCase(factoryName))
                .fetch();
    }

    @Override
    public List<Workcenter> findByFactoryCodeContaining(String factoryCode) {
        QWarehouse factory = QWarehouse.warehouse;
        return queryFactory
                .selectFrom(workcenter)
                .where(workcenter.factory.code.containsIgnoreCase(factoryCode))
                .fetch();
    }

    @Override
    public Optional<Workcenter> findOneByFactoryCode(String factoryCode) {
        QWarehouse factory = QWarehouse.warehouse;
        Workcenter result = queryFactory
                .selectFrom(workcenter)
                .join(workcenter.factory, factory).fetchJoin() // JOIN FETCH
                .where(workcenter.factory.code.eq(factoryCode))
                .fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public List<Workcenter> findByProcessNameContaining(String processName) {
        QProcessDetails processDetails = QProcessDetails.processDetails;
        return queryFactory
                .selectFrom(workcenter)
                .where(workcenter.processDetails.name.containsIgnoreCase(processName))
                .fetch();
    }

    @Override
    public List<Workcenter> findByProcessCodeContaining(String processCode) {
        QProcessDetails processDetails = QProcessDetails.processDetails;
        return queryFactory
                .selectFrom(workcenter)
                .where(workcenter.processDetails.code.containsIgnoreCase(processCode))
                .fetch();
    }

    @Override
    public Optional<Workcenter> findOneByProcessCode(String processCode) {
        QProcessDetails processDetails = QProcessDetails.processDetails;
        Workcenter result = queryFactory
                .selectFrom(workcenter)
                .join(workcenter.processDetails, processDetails).fetchJoin() // JOIN FETCH
                .where(workcenter.processDetails.code.eq(processCode))
                .fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public List<Workcenter> findByEquipmentNameContaining(String equipmentName) {
        QEquipmentData equipmentData = QEquipmentData.equipmentData;
        return queryFactory
                .selectFrom(workcenter)
                .join(workcenter.equipmentList, equipmentData).fetchJoin() // JOIN FETCH
                .where(equipmentData.equipmentName.containsIgnoreCase(equipmentName))
                .fetch();
    }

    @Override
    public List<Workcenter> findByEquipmentModelNumberContaining(String equipmentModelNumber) {
        QEquipmentData equipmentData = QEquipmentData.equipmentData;
        return queryFactory
                .selectFrom(workcenter)
                .join(workcenter.equipmentList, equipmentData).fetchJoin() // JOIN FETCH
                .where(equipmentData.modelName.containsIgnoreCase(equipmentModelNumber))
                .fetch();
    }

    /**
     * 공통 작업자 배정 내역 조회 메서드
     * @param workcenterId 작업장 ID
     * @param optionalDate 선택적 날짜 (오늘 날짜가 있으면 해당 날짜 기준 조회, 없으면 전체 조회)
     * @return 작업자 배정 내역 리스트
     */

    @Override
    public List<WorkerAssignment> getWorkerAssignments(Long workcenterId, Optional<LocalDate> optionalDate) {
        QWorkerAssignment workerAssignment = QWorkerAssignment.workerAssignment;

        // 오늘 날짜가 있을 경우, 해당 날짜에 맞는 작업자 배정을 조회
        if (optionalDate.isPresent()) {
            LocalDate date = optionalDate.get();
            return queryFactory
                    .selectFrom(workerAssignment)
                    .join(workerAssignment.workcenter, workcenter) // 작업장과 WorkerAssignment를 연결하는 Join
                    .fetchJoin() // 데이터 함께 가져오기
                    .where(workerAssignment.workcenter.id.eq(workcenterId)
                            .and(workerAssignment.assignmentDate.eq(date))) // 오늘 날짜 기준
                    .fetch();
        } else {
            // 날짜가 없을 경우, 전체 작업자 배정을 날짜 순으로 조회
            return queryFactory
                    .selectFrom(workerAssignment)
                    .where(workerAssignment.workcenter.id.eq(workcenterId))
                    .orderBy(workerAssignment.assignmentDate.desc()) // 최신 날짜 순 정렬
                    .fetch();
        }
    }



//    /**
//     * 오늘의 작업자
//     * @param workcenterId 작업장 ID
//     * @param today 오늘 날짜
//     * @return
//     */
//    @Override
//    public List<WorkerAssignment> findTodayWorkerAssignmentsByWorkcenterId(Long workcenterId, LocalDate today) {
//        QWorkerAssignment workerAssignment = QWorkerAssignment.workerAssignment;
//
//        return queryFactory
//                .selectFrom(workerAssignment)
//                .join(workerAssignment.workcenter, workcenter) // 작업장과 WorkerAssignment를 연결하는 Join
//                .fetchJoin() // 데이터 함께 가져오기
//                .where(workerAssignment.workcenter.id.eq(workcenterId)
//                        .and(workerAssignment.assignmentDate.eq(today)))
//                .fetch();
//    }
//
//    /**
//     * 배정일 최신순으로 정렬하여 이력 조회
//     * @param workcenterId 작업장 ID
//     * @return
//     */
//
//    @Override
//    public List<WorkerAssignment> findWorkerAssignmentsByWorkcenterId(Long workcenterId) {
//        QWorkerAssignment workerAssignment = QWorkerAssignment.workerAssignment;
//        return queryFactory
//                .selectFrom(workerAssignment)
//                .where(workerAssignment.workcenter.id.eq(workcenterId))
//                .orderBy(workerAssignment.assignmentDate.desc())
//                .fetch();
//    }


}
