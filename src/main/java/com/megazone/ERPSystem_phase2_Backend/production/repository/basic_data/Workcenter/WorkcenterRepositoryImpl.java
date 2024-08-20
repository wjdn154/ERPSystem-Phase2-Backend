package com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.Workcenter;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse.QWarehouse;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.QWorkcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.QWorkerAssignment;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.WorkerAssignment;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.QEquipmentData;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.QWorkcenter.workcenter;

@Repository
@RequiredArgsConstructor
public class WorkcenterRepositoryImpl implements WorkcenterRepositoryCustom {

    private final JPAQueryFactory queryFactory;

//    @Override
//    public List<Workcenter> findByFactoryNameContaining(String factoryName) {
//        QWarehouse warehouse = QWarehouse.warehouse;
//        return queryFactory
//                .selectFrom(workcenter)
//                .join(workcenter.factory, warehouse) // 직접적으로 조인
//                .where(warehouse.name.containsIgnoreCase(factoryName))
//                .fetch();
//
//    @Override
//    public List<Workcenter> findByFactoryCodeContaining(String factoryCode) {
//        return queryFactory
//                .selectFrom(workcenter)
//                .where(workcenter.factory.code.containsIgnoreCase(factoryCode))
//                .fetch();
//    }

    @Override
    public List<Workcenter> findByProcessNameContaining(String processName) {
        return queryFactory
                .selectFrom(workcenter)
                .where(workcenter.processDetails.name.containsIgnoreCase(processName))
                .fetch();
    }

    @Override
    public List<Workcenter> findByProcessCodeContaining(String processCode) {
        return queryFactory
                .selectFrom(workcenter)
                .where(workcenter.processDetails.code.containsIgnoreCase(processCode))
                .fetch();
    }

    @Override
    public List<Workcenter> findByEquipmentNameContaining(String equipmentName) {
        QEquipmentData equipmentData = QEquipmentData.equipmentData;
        return queryFactory
                .selectFrom(workcenter)
                .join(workcenter.equipmentList, equipmentData)
                .where(equipmentData.equipmentName.containsIgnoreCase(equipmentName))
                .fetch();
    }

    @Override
    public List<Workcenter> findByEquipmentModelNumberContaining(String equipmentModelNumber) {
        QEquipmentData equipmentData = QEquipmentData.equipmentData;
        return queryFactory
                .selectFrom(workcenter)
                .join(workcenter.equipmentList, equipmentData)
                .where(equipmentData.modelNumber.containsIgnoreCase(equipmentModelNumber))
                .fetch();
    }

    @Override
    public List<WorkerAssignment> findTodayWorkerAssignmentsByWorkcenterId(Long workcenterId, LocalDate today) {
        QWorkerAssignment workerAssignment = QWorkerAssignment.workerAssignment;
        return queryFactory
                .selectFrom(workerAssignment)
                .where(workerAssignment.workcenter.id.eq(workcenterId)
                        .and(workerAssignment.assignmentDate.eq(today)))
                .fetch();
    }

    @Override
    public List<WorkerAssignment> findWorkerAssignmentsByWorkcenterId(Long workcenterId) {
        QWorkerAssignment workerAssignment = QWorkerAssignment.workerAssignment;
        return queryFactory
                .selectFrom(workerAssignment)
                .where(workerAssignment.workcenter.id.eq(workcenterId))
                .orderBy(workerAssignment.assignmentDate.desc())
                .fetch();
    }


}
