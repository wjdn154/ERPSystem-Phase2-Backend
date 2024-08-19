//package com.megazone.ERPSystem_phase2_Backend.production.service.basic_data;
//
//import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_registration.Warehouse;
//import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
//import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.dto.WorkcenterDTO;
//import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.enums.WorkcenterType;
//import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto.EquipmentDataDTO;
//import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto.WorkerAssignmentDTO;
//import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProcessDetails;
//import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.Workcenter.WorkcenterRepository;
//import com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.workcenter.WorkcenterRegistrationServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.dao.DataIntegrityViolationException;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class WorkcenterRegistrationServiceImplTest {
//
//    @Mock
//    private WorkcenterRepository workcenterRepository;
//
//    @InjectMocks
//    private WorkcenterRegistrationServiceImpl workcenterRegistrationService;
//
//    private Workcenter workcenter;
//    private WorkcenterDTO workcenterDTO;
//    private Warehouse warehouse;
//    private ProcessDetails processDetails;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        // 초기화된 Warehouse와 ProcessDetails
//        warehouse = new Warehouse();
//        warehouse.setCode("WH001");
//
//        processDetails = new ProcessDetails();
//        processDetails.setCode("PR001");
//
//        // 초기화된 Workcenter 객체
//        workcenter = new Workcenter();
//        workcenter.setId(1L);
//        workcenter.setCode("WC001");
//        workcenter.setName("Assembly Line");
//        workcenter.setWorkcenterType(WorkcenterType.ASSEMBLY);
//        workcenter.setDescription("Assembly Line Description");
//        workcenter.setIsActive(false);
////        workcenter.setFactory(warehouse);
//        workcenter.setProcessDetails(processDetails);
//
//        // 초기화된 WorkcenterDTO 객체
//        workcenterDTO = new WorkcenterDTO();
//        workcenterDTO.setCode("WC001");
//        workcenterDTO.setName("Updated Assembly Line");
//        workcenterDTO.setWorkcenterType(WorkcenterType.ASSEMBLY);
//        workcenterDTO.setDescription("Updated Assembly Line Description");
//        workcenterDTO.setIsActive(false);
////        workcenterDTO.setFactoryCode("WH001");
//        workcenterDTO.setProcessCode("PR001");
//        workcenterDTO.setEquipmentList(new ArrayList<>()); // 예시로 빈 리스트 사용
//        workcenterDTO.setWorkerAssignments(new ArrayList<>()); // 예시로 빈 리스트 사용
//    }
//
//    @Test
//    void testSaveWorkcenter() {
//        // Given
//        when(workcenterRepository.save(any(Workcenter.class))).thenReturn(workcenter);
//
//        // When
//        Workcenter savedWorkcenter = workcenterRegistrationService.save(workcenter);
//
//        // Then
//        assertNotNull(savedWorkcenter);
//        assertEquals(workcenter.getCode(), savedWorkcenter.getCode());
//        verify(workcenterRepository, times(1)).save(workcenter);
//    }
//
//    /**
//     * save: 저장 시 중복 코드 검증
//     */
//    @Test
//    void testSaveWorkcenterWithDuplicateCode() {
//        // Given
//        when(workcenterRepository.save(any(Workcenter.class))).thenThrow(DataIntegrityViolationException.class);
//
//        // When & Then
//        assertThrows(RuntimeException.class, () -> workcenterRegistrationService.save(workcenter));
//        verify(workcenterRepository, times(1)).save(workcenter);
//    }
//
//    @Test
//    void testDeleteWorkcenterById() {
//        // Given
//        when(workcenterRepository.findById(1L)).thenReturn(Optional.of(workcenter));
//
//        // When
//        workcenterRegistrationService.deleteByIds(List.of(1L));
//
//        // Then
//        verify(workcenterRepository, times(1)).deleteAllById(List.of(1L));
//    }
//
//    /**
//     * 삭제 시 사용 중인 작업장 확인
//     */
//    @Test
//    void testDeleteWorkcenterByIdThrowsExceptionWhenActive() {
//        // Given
//        workcenter.setIsActive(true); // 작업장 사용 중
//        when(workcenterRepository.findById(1L)).thenReturn(Optional.of(workcenter));
//
//        // When & Then
//        assertThrows(RuntimeException.class, () -> workcenterRegistrationService.deleteByIds(List.of(1L)));
//        verify(workcenterRepository, never()).deleteById(anyLong());
//    }
//
//    @Test
//    void testUpdateWorkcenter() {
//        // Given
//        when(workcenterRepository.findByCode("WC001")).thenReturn(Optional.of(workcenter));
//        when(workcenterRepository.save(any(Workcenter.class))).thenReturn(workcenter);
//
//        // When
//        Workcenter updatedWorkcenter = workcenterRegistrationService.updateWorkcenter("WC001", workcenterDTO);
//
//        // Then
//        assertNotNull(updatedWorkcenter);
//        assertEquals(workcenterDTO.getName(), updatedWorkcenter.getName());
//        assertEquals(workcenterDTO.getDescription(), updatedWorkcenter.getDescription());
//        assertEquals(workcenterDTO.getWorkcenterType(), updatedWorkcenter.getWorkcenterType());
////        assertEquals(workcenterDTO.getFactoryCode(), updatedWorkcenter.getFactory().getCode());
//        assertEquals(workcenterDTO.getProcessCode(), updatedWorkcenter.getProcessDetails().getCode());
//        verify(workcenterRepository, times(1)).findByCode("WC001");
//        verify(workcenterRepository, times(1)).save(updatedWorkcenter);
//    }
//
//    /**
//     * 중복 코드가 없을 때만 업데이트 진행
//     */
//    @Test
//    void testUpdateWorkcenterWithDuplicateCode() {
//        // Given
//        Workcenter anotherWorkcenter = new Workcenter();
//        anotherWorkcenter.setId(2L);
//        anotherWorkcenter.setCode("WC002");
//
//        when(workcenterRepository.findByCode("WC001")).thenReturn(Optional.of(workcenter));
//        when(workcenterRepository.findByCode("WC002")).thenReturn(Optional.of(anotherWorkcenter));
//
//        // Update 코드 변경 시, 동일한 코드가 존재하는 경우 예외 발생 테스트
//        workcenterDTO.setCode("WC002");
//
//        // When & Then
//        assertThrows(RuntimeException.class, () -> workcenterRegistrationService.updateWorkcenter("WC001", workcenterDTO));
//        verify(workcenterRepository, times(1)).findByCode("WC001");
//        verify(workcenterRepository, times(1)).findByCode("WC002");
//    }
//}
