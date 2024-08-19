//package com.megazone.ERPSystem_phase2_Backend.production.service.basic_data;
//
//import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.Workcenter;
//import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.dto.WorkcenterDTO;
//import com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.enums.WorkcenterType;
//import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProcessDetails;
//import com.megazone.ERPSystem_phase2_Backend.logistics.model.warehouse_management.warehouse_registration.Warehouse;
//import com.megazone.ERPSystem_phase2_Backend.production.repository.basic_data.Workcenter.WorkcenterRepository;
//import com.megazone.ERPSystem_phase2_Backend.production.service.basic_data.workcenter.WorkcenterRegistrationServiceImpl;
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
//class WorkcenterServiceImplTest {
//
//    @Mock
//    private WorkcenterRepository workcenterRepository;
//
//    @InjectMocks
//    private WorkcenterRegistrationServiceImpl workcenterRegistrationService;
//
//    private Workcenter workcenter;
//    private Warehouse warehouse;
//    private ProcessDetails processDetails;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        // Warehouse와 ProcessDetails를 포함한 모든 필드를 초기화
//        warehouse = new Warehouse();
//        warehouse.setId(1L);
//        warehouse.setCode("WH001");
//        warehouse.setName("Main Warehouse");
//        warehouse.setProductionProcess("Assembly Process");
//        warehouse.setIsActive(true);
//
//        processDetails = new ProcessDetails();
//        processDetails.setId(1L);
//        // ProcessDetails 필드 초기화 (예시로만 간단히 작성)
//
//        workcenter = new Workcenter(
//                1L,                    // id
//                "WC123",               // code
//                WorkcenterType.ASSEMBLY, // workcenterType
//                "Assembly Line",       // name
//                "Assembly Line Description", // description
//                true,                  // isActive
//                warehouse,             // factory
//                processDetails         // processDetails
//        );
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
//        assertEquals(workcenter.getName(), savedWorkcenter.getName());
//        assertEquals(workcenter.getWorkcenterType(), savedWorkcenter.getWorkcenterType());
//        assertEquals(workcenter.getDescription(), savedWorkcenter.getDescription());
//        assertEquals(workcenter.getIsActive(), savedWorkcenter.getIsActive());
//        assertEquals(workcenter.getFactory(), savedWorkcenter.getFactory());
//        assertEquals(workcenter.getProcessDetails(), savedWorkcenter.getProcessDetails());
//    }
//
//    @Test
//    void testFindByName() {
//        // Given
//        when(workcenterRepository.findByName("Assembly Line")).thenReturn(Arrays.asList(workcenter));
//
//        // When
//        List<Workcenter> result = workcenterRegistrationService.findByName("Assembly Line");
//
//        // Then
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals("Assembly Line", result.get(0).getName());
//        assertEquals(workcenter.getFactory(), result.get(0).getFactory());
//        assertEquals(workcenter.getProcessDetails(), result.get(0).getProcessDetails());
//    }
//
//    @Test
//    void testFindByCode() {
//        // Given
//        when(workcenterRepository.findByCode("WC123")).thenReturn(Arrays.asList(workcenter));
//
//        // When
//        List<Workcenter> result = workcenterRegistrationService.findByCode("WC123");
//
//        // Then
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals("WC123", result.get(0).getCode());
//        assertEquals(workcenter.getFactory(), result.get(0).getFactory());
//        assertEquals(workcenter.getProcessDetails(), result.get(0).getProcessDetails());
//    }
//
//    @Test
//    void testFindByActive() {
//        // Given
//        Workcenter workcenter2 = new Workcenter(
//                2L, "WC124", WorkcenterType.PAINT, "Paint Line", "Paint Line Description", true, warehouse, processDetails
//        );
//        when(workcenterRepository.findByActive(true)).thenReturn(Arrays.asList(workcenter, workcenter2));
//
//        // When
//        List<Workcenter> result = workcenterRegistrationService.findByActive(true);
//
//        // Then
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        assertTrue(result.stream().allMatch(Workcenter::getIsActive));
//        assertEquals(workcenter.getFactory(), result.get(0).getFactory());
//        assertEquals(workcenter.getProcessDetails(), result.get(0).getProcessDetails());
//    }
//
//    @Test
//    void testFindById() {
//        // Given
//        when(workcenterRepository.findById(1L)).thenReturn(Optional.of(workcenter));
//
//        // When
//        Workcenter result = workcenterRegistrationService.findById(1L);
//
//        // Then
//        assertNotNull(result);
//        assertEquals("Assembly Line", result.getName());
//        assertEquals("WC123", result.getCode());
//        assertEquals(workcenter.getFactory(), result.getFactory());
//        assertEquals(workcenter.getProcessDetails(), result.getProcessDetails());
//    }
//
//    @Test
//    void testDeleteById() {
//        // Given
//        doNothing().when(workcenterRepository).deleteById(1L);
//
//        // When
//        workcenterRegistrationService.deleteById(1L);
//
//        // Then
//        verify(workcenterRepository, times(1)).deleteById(1L);
//    }
//}
