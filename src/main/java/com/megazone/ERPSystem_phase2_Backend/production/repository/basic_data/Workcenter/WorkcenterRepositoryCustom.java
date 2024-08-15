package com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.Workcenter;

import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;

import java.util.List;

public interface WorkcenterRepositoryCustom {
    /**
     * 이름으로 작업장 검색 메서드
     *
     * @param name 작업장 이름
     * @return 해당 이름을 가진 Workcenter 리스트
     */
    List<Workcenter> findByName(String name);

    /**
     * 지정코드로 작업장 검색 메서드
     * @param code 작업장 코드
     * @return 해당 코드를 가진 Workcenter 리스트
     */
    List<Workcenter> findByCode(String code);

    /**
     * 사용 중인 작업장 조회 메서드
     * @param active 작업장 사용 여부
     * @return 사용 여부에 따른 Workcenter 리스트
     */
    List<Workcenter> findByActive(boolean active);
}
