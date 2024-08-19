package com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.Workcenter;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.QWorkcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.enums.WorkcenterType;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.QWorkerAssignment;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.WorkerAssignment;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.equipment.QEquipmentData;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class WorkcenterRepositoryImpl implements WorkcenterRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    QWorkcenter workcenter = QWorkcenter.workcenter;

    @Override
    public List<Workcenter> findByNameContaining(String name) {
        return queryFactory
                .selectFrom(QWorkcenter.workcenter)
                .where(QWorkcenter.workcenter.name.containsIgnoreCase(name))
                .fetch();
    }

    @Override
    public List<Workcenter> findByCodeContaining(String code) {
        return queryFactory
                .selectFrom(QWorkcenter.workcenter)
                .where(QWorkcenter.workcenter.code.containsIgnoreCase(code))
                .fetch();
    }

    @Override
    public List<Workcenter> findByActive(boolean isActive) {
        return queryFactory
                .selectFrom(QWorkcenter.workcenter)
                .where(QWorkcenter.workcenter.isActive.eq(isActive))
                .fetch();
    }

    @Override
    public List<Workcenter> findByFactoryNameContaining(String factoryName) {
        return queryFactory
                .selectFrom(QWorkcenter.workcenter)
                .where(QWorkcenter.workcenter.factory.name.containsIgnoreCase(factoryName))
                .fetch();
    }

    @Override
    public List<Workcenter> findByFactoryCodeContaining(String factoryCode) {
        return queryFactory
                .selectFrom(QWorkcenter.workcenter)
                .where(QWorkcenter.workcenter.factory.code.containsIgnoreCase(factoryCode))
                .fetch();
    }

    @Override
    public List<Workcenter> findByProcessNameContaining(String processName) {
        return queryFactory
                .selectFrom(QWorkcenter.workcenter)
                .where(QWorkcenter.workcenter.processDetails.name.containsIgnoreCase(processName))
                .fetch();
    }

    @Override
    public List<Workcenter> findByProcessCodeContaining(String processCode) {
        return queryFactory
                .selectFrom(QWorkcenter.workcenter)
                .where(QWorkcenter.workcenter.processDetails.code.containsIgnoreCase(processCode))
                .fetch();
    }

    @Override
    public List<Workcenter> findByTypeIn(List<WorkcenterType> types) {
        return queryFactory
                .selectFrom(QWorkcenter.workcenter)
                .where(QWorkcenter.workcenter.workcenterType.in(types))
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
    public Workcenter findWorkcenterDetailsById(Long id) {
        return queryFactory
                .selectFrom(workcenter)
                .where(workcenter.id.eq(id))
                .fetchOne();
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
