package com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.Workcenter;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.enums.WorkcenterType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkcenterRepository extends JpaRepository<Workcenter, Long>, WorkcenterRepositoryCustom {
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
    List<Workcenter> findByIsActive(boolean isActive);

    /**
     * 작업장 유형으로 해당 작업장 조회 메서드
     * @param workcenterType enum 작업장 유형
     * @return 선택한 작업장 유형에 해당하는 Workcenter 리스트
     */
    List<Workcenter> findByWorkcenterTypeIn(List<WorkcenterType> workcenterType);
}
