package com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.worker;

import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.Worker;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto.ListWorkerDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.QWorker.worker;

@Repository
@RequiredArgsConstructor
public class WorkerRepositoryImpl implements WorkerRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    /**부서가 생산인 모든 작업자를 조회함. 작업자 목록을 반환함.
     * */
    @Override
    public List<ListWorkerDTO> findAllWorkerByDepartment() {
        return queryFactory
                .select(Projections.fields(ListWorkerDTO.class,     //ListWorkerDTO에 담길 필드를 명시
                        worker.id.as("id"),
                        worker.trainingStatus.stringValue().as("trainingStatus"),
                        worker.employee.employeeNumber.as("employeeNumber"),
                        worker.employee.firstName.as("employeeFirstName"),
                        worker.employee.lastName.as("employeeLastName"),
                        worker.employee.employmentStatus.stringValue().as("employmentStatus"),
                        worker.employee.employmentType.stringValue().as("employmentType"),
                        worker.employee.department.departmentName.as("departmentName"),
                        worker.employee.position.positionName.as("positionName"),
                        worker.employee.jobTitle.jobTitleName.as("jobTitleName")
                ))
                .from(worker)
                .where(worker.employee.department.departmentName.contains("생산"))
                .orderBy(worker.employee.firstName.asc())     //오름차순으로 정렬
                .fetch();
    }

}
