//package com.megazone.ERPSystem_phase2_Backend.production.service.basic_information;
//
//import com.megazone.ERPSystem_phase2_Backend.production.model.basic_information.Workcenter;
//import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_information.Workcenter.WorkcenterRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class WorkcenterRegistrationServiceImplTest {
//
//    @Mock
//    private WorkcenterRepository workcenterRepository; // Mock 객체로 대체된 WorkcenterRepository
//
//    @InjectMocks
//    private WorkcenterRegistrationServiceImpl workcenterRegistrationService; // 테스트할 대상 클래스
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this); // Mock 객체 초기화
//    }
//
//    /**
//     * findByName 테스트 메서드
//     * 특정 이름을 가진 Workcenter를 조회하는 기능을 테스트함
//     */
//    @Test
//    void findByName() {
//        Workcenter workcenter = new Workcenter(1L, "W123", "작업장1", "설명", 10L, true);
//        when(workcenterRepository.findByName("작업장1")).thenReturn(Arrays.asList(workcenter)); // Mock 동작 정의
//
//        List<Workcenter> result = workcenterRegistrationService.findByName("작업장1"); // 테스트 실행
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals("작업장1", result.get(0).getName());
//    }
//
//    /**
//     * findByCode 테스트 메서드
//     * 특정 코드를 가진 Workcenter를 조회하는 기능을 테스트함
//     */
//    @Test
//    void findByCode() {
//        Workcenter workcenter = new Workcenter(1L, "W123", "작업장1", "설명", 10L, true);
//        when(workcenterRepository.findByCode("W123")).thenReturn(Arrays.asList(workcenter)); // Mock 동작 정의
//
//        List<Workcenter> result = workcenterRegistrationService.findByCode("W123"); // 테스트 실행
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals("W123", result.get(0).getCode());
//    }
//
//    /**
//     * findByActive 테스트 메서드
//     * 사용중인 Workcenter를 조회하는 기능을 테스트함
//     */
//    @Test
//    void findByActive() {
//        Workcenter workcenter1 = new Workcenter(1L, "W123", "작업장1", "설명", 10L, true);
//        Workcenter workcenter2 = new Workcenter(2L, "W124", "작업장2", "설명", 5L, true);
//        when(workcenterRepository.findByActive(true)).thenReturn(Arrays.asList(workcenter1, workcenter2)); // Mock 동작 정의
//
//        List<Workcenter> result = workcenterRegistrationService.findByActive(true); // 테스트 실행
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        assertTrue(result.stream().allMatch(Workcenter::isActive)); // 모두 active 상태인지 확인
//    }
//
//    /**
//     * save 테스트 메서드
//     * Workcenter 객체를 저장하는 기능을 테스트함
//     */
//    @Test
//    void save() {
//        Workcenter workcenter = new Workcenter(1L, "W123", "작업장1", "설명", 10L, true);
//        when(workcenterRepository.save(any(Workcenter.class))).thenReturn(workcenter); // Mock 동작 정의
//
//        Workcenter result = workcenterRegistrationService.save(workcenter); // 테스트 실행
//
//        assertNotNull(result);
//        assertEquals("작업장1", result.getName());
//        assertEquals("W123", result.getCode());
//    }
//
//    /**
//     * findById 테스트 메서드
//     * ID로 Workcenter 객체를 조회하는 기능을 테스트함
//     */
//    @Test
//    void findById() {
//        Workcenter workcenter = new Workcenter(1L, "W123", "작업장1", "설명", 10L, true);
//        when(workcenterRepository.findById(1L)).thenReturn(Optional.of(workcenter)); // Mock 동작 정의
//
//        Workcenter result = workcenterRegistrationService.findById(1L); // 테스트 실행
//
//        assertNotNull(result);
//        assertEquals("작업장1", result.getName());
//        assertEquals("W123", result.getCode());
//    }
//
//    /**
//     * deleteById 테스트 메서드
//     * ID로 Workcenter 객체를 삭제하는 기능을 테스트함
//     */
//    @Test
//    void deleteById() {
//        doNothing().when(workcenterRepository).deleteById(1L); // Mock 동작 정의
//
//        workcenterRegistrationService.deleteById(1L); // 테스트 실행
//
//        verify(workcenterRepository, times(1)).deleteById(1L); // 메서드 호출 확인
//    }
//}
