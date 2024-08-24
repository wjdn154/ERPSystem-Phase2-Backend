//package com.megazone.ERPSystem_phase2_Backend.production.service.routing_management;
//
//import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.Product;
//import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.ProductGroup;
//import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductDetailDto;
//import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.Product.ProductRepository;
//import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.ProductGroup.ProductGroupRepository;
//import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProcessDetails;
//import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.ProductionRouting;
//import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.RoutingStep;
//import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto.ProcessDetailsDTO;
//import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto.ProductionRoutingDTO;
//import com.megazone.ERPSystem_phase2_Backend.production.model.routing_management.dto.RoutingStepDTO;
//import com.megazone.ERPSystem_phase2_Backend.production.repository.routing_management.ProcessDetails.ProcessDetailsRepository;
//import com.megazone.ERPSystem_phase2_Backend.production.repository.routing_management.ProductionRouting.ProductionRoutingRepository;
//import com.megazone.ERPSystem_phase2_Backend.production.service.routing_management.ProductionRouting.ProductionRoutingServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class ProductionRoutingServiceImplTest {
//
//    @Mock
//    private ProductionRoutingRepository productionRoutingRepository;
//
//    @Mock
//    private ProcessDetailsRepository processDetailsRepository;
//
//    @Mock
//    private ProductRepository productRepository;
//
//    @Mock
//    private ProductGroupRepository productGroupRepository;
//
//    @InjectMocks
//    private ProductionRoutingServiceImpl productionRoutingService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void createProductionRoutingWithSteps_Success() {
//        // Given
//        ProductionRoutingDTO routingDTO = ProductionRoutingDTO.builder()
//                .code("ROUTING_01")
//                .name("Test Routing")
//                .routingStepDTOList(new ArrayList<>())
//                .products(new ArrayList<>())
//                .build();
//
//        ProcessDetailsDTO processDetailsDTO = ProcessDetailsDTO.builder()
//                .id(1L)
//                .code("PROCESS_01")
//                .name("Sample Process")
//                .build();
//
//        RoutingStepDTO stepDTO = RoutingStepDTO.builder()
//                .stepOrder(1L)
//                .processId(1L)
//                .processDetailsDTO(processDetailsDTO)
//                .build();
//
//        routingDTO.getRoutingStepDTOList().add(stepDTO);
//
//        ProcessDetails processDetails = new ProcessDetails();
//        processDetails.setId(1L);
//        processDetails.setCode("PROCESS_01");
//        processDetails.setName("Sample Process");
//
//        ProductionRouting productionRouting = new ProductionRouting();
//        productionRouting.setId(1L);
//        productionRouting.setCode("ROUTING_01");
//        productionRouting.setName("Test Routing");
//
//        // Mocking repository responses
//        when(productionRoutingRepository.existsByCode(anyString())).thenReturn(false);
//        when(productionRoutingRepository.save(any(ProductionRouting.class))).thenReturn(productionRouting);
//        when(processDetailsRepository.findByCodeContainingOrNameContaining(anyString(), anyString()))
//                .thenReturn(List.of(processDetails));
//
//        // When
//        ProductionRoutingDTO createdRouting = productionRoutingService.createProductionRoutingWithSteps(routingDTO);
//
//        // Then
//        assertNotNull(createdRouting);
//        assertEquals("ROUTING_01", createdRouting.getCode());
//        verify(productionRoutingRepository, times(1)).save(any(ProductionRouting.class));
//    }
//
//
//
//
//
//
//    @Test
//    void deleteProductionRouting_Success() {
//        // given
//        ProductionRouting existingRouting = new ProductionRouting();
//        existingRouting.setId(1L);
//        existingRouting.setCode("ROUTING_03");
//        existingRouting.setActive(false);
//        existingRouting.setRoutingSteps(new ArrayList<>()); // Initialize to avoid null
//
//        when(productionRoutingRepository.findById(1L)).thenReturn(Optional.of(existingRouting));
//
//        // when
//        ProductionRoutingDTO deletedRouting = productionRoutingService.deleteProductionRouting(1L);
//
//        // then
//        assertNotNull(deletedRouting);
//        assertEquals("ROUTING_03", deletedRouting.getCode());
//        verify(productionRoutingRepository, times(1)).delete(any(ProductionRouting.class));
//    }
//
//    @Test
//    void updateProductionRouting_Success() {
//        // given
//        ProductionRoutingDTO routingDTO = new ProductionRoutingDTO();
//        routingDTO.setCode("ROUTING_02");
//        routingDTO.setName("Updated Routing");
//
//        RoutingStepDTO stepDTO = new RoutingStepDTO();
//        stepDTO.setStepOrder(1L);
//        stepDTO.setProcessId(1L);
//        routingDTO.setRoutingStepDTOList(List.of(stepDTO));
//
//        ProductionRouting existingRouting = new ProductionRouting();
//        existingRouting.setId(1L);
//        existingRouting.setCode("ROUTING_02");
//        existingRouting.setRoutingSteps(Arrays.asList(new RoutingStep()));
//
//        ProcessDetails processDetails = new ProcessDetails();
//        processDetails.setId(1L);
//        processDetails.setCode("PROCESS_01");
//
//        when(productionRoutingRepository.findById(1L)).thenReturn(Optional.of(existingRouting));
//        when(processDetailsRepository.findById(1L)).thenReturn(Optional.of(processDetails));  // Correct object returned
//        when(productionRoutingRepository.save(any(ProductionRouting.class))).thenReturn(existingRouting);
//
//        // when
//        ProductionRoutingDTO updatedRouting = productionRoutingService.updateProductionRouting(1L, routingDTO);
//
//        // then
//        assertNotNull(updatedRouting);
//        assertEquals("ROUTING_02", updatedRouting.getCode());
//        verify(productionRoutingRepository, times(1)).save(any(ProductionRouting.class));
//    }
//
//}
