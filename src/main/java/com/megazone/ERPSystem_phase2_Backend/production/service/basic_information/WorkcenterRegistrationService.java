//package com.megazone.ERPSystem_phase2_Backend.production.service.basic_information;
//
//import com.megazone.ERPSystem_phase2_Backend.production.model.basic_information.Workcenter;
//
//import java.util.List;
//
//public interface WorkcenterRegistrationService {
//
//    /**
//     * 이름으로 작업장 검색 메서드
//     * @param name 작업장명
//     * @return 해당 이름을 가진 Workcenter 리스트
//     */
//    List<Workcenter> findByName(String name);
//
//    /**
//     * 지정 code로 작업장 검색 메서드
//     * @param code 작업장 ID
//     * @return 해당 ID를 가진 Workcenter 객체
//     */
//    List<Workcenter> findByCode(String code);
//
//    /**
//     * 사용 여부로 작업장 검색 메서드
//     * @param active 작업장 사용 여부
//     * @return 사용 여부에 따른 Workcenter 리스트
//     */
//    List<Workcenter> findByActive(Boolean active);
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
//    /**
//     * ID로 작업장 삭제 메서드
//     * @param id 삭제할 Workcenter ID
//     */
//    void deleteById(Long id);
//}
