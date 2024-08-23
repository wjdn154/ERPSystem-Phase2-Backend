package com.megazone.ERPSystem_phase2_Backend.production.service.routing_management;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProductionRouting;
import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto.ProductionRoutingDTO;
import com.megazone.ERPSystem_phase2_Backend.production.repository.routing_management.ProductionRouting.ProductionRoutingRepository;
import com.megazone.ERPSystem_phase2_Backend.production.service.routing_management.ProductionRouting.ProductionRoutingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

public class ProductionRoutingServiceImplTest {

    @Mock
    private ProductionRoutingRepository productionRoutingRepository;

    @InjectMocks
    private ProductionRoutingServiceImpl productionRoutingService;

    private ProductionRoutingDTO validDto;
    private ProductionRouting existingRouting;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        validDto = new ProductionRoutingDTO();
        validDto.setCode("ROUTING001");
        validDto.setName("Test Routing");

        existingRouting = new ProductionRouting();
        existingRouting.setId(1L);
        existingRouting.setCode("ROUTING001");  // 기존의 "ORIGINAL_CODE"를 "ROUTING001"로 수정
    }

//    @Test
//    void createRoutingWithValidDTO_savesRouting() {
//        // Given
//        when(productionRoutingRepository.existsByCode("ROUTING001")).thenReturn(false);
//        when(productionRoutingRepository.save(any(ProductionRouting.class))).thenReturn(existingRouting);
//
//        // When
//        ProductionRouting result = productionRoutingService.createProductionRoutingWithSteps(validDto, List.of());
//
//        // Then
//        assertNotNull(result);
//        assertEquals("ROUTING001", result.getCode());
//    }

//    @Test
//    void createRoutingWithExistingCode_throwsException() {
//        // Given
//        when(productionRoutingRepository.existsByCode("ROUTING001")).thenReturn(true);
//
//        // When & Then
//        assertThrows(IllegalArgumentException.class, () -> {
//            productionRoutingService.createProductionRoutingWithSteps(validDto, List.of());
//        });
//    }
//
//    @Test
//    void updateRoutingWithValidDTO_updatesRouting() {
//        // Given
//        when(productionRoutingRepository.findById(1L)).thenReturn(Optional.of(existingRouting));
//        when(productionRoutingRepository.save(any(ProductionRouting.class))).thenReturn(existingRouting);
//
//        // When
//        Optional<ProductionRouting> result = productionRoutingService.updateProductionRouting(1L, validDto);
//
//        // Then
//        assertTrue(result.isPresent());
//        assertEquals("ROUTING001", result.get().getCode());
//    }
}
