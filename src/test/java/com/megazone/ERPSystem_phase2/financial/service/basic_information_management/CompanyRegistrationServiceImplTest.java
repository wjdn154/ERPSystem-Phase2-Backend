package com.megazone.ERPSystem_phase2.financial.service.basic_information_management;

import com.megazone.ERPSystem_phase2.financial.model.basic_information_management.company_registration.Representative;
import com.megazone.ERPSystem_phase2.financial.repository.basic_information_management.company_registration.Representative.RepresentativeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CompanyRegistrationServiceImplTest {

    @Mock
    private RepresentativeRepository representativeRepository;

    @InjectMocks
    private CompanyRegistrationServiceImpl companyRegistrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByName() {
        Representative representative = new Representative(1L, "홍길동", "123456789", false);
        when(representativeRepository.findByName("홍길동")).thenReturn(Arrays.asList(representative));

        List<Representative> result = companyRegistrationService.findByName("홍길동");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("홍길동", result.get(0).getName());
    }

    @Test
    void save() {
        Representative representative = new Representative(1L, "홍길동", "123456789", false);
        when(representativeRepository.save(any(Representative.class))).thenReturn(representative);

        Representative result = companyRegistrationService.save(representative);

        assertNotNull(result);
        assertEquals("홍길동", result.getName());
        assertEquals("123456789", result.getIdNumber());
    }

    @Test
    void findById() {
        Representative representative = new Representative(1L, "홍길동", "123456789", false);
        when(representativeRepository.findById(1L)).thenReturn(Optional.of(representative));

        Representative result = companyRegistrationService.findById(1L);

        assertNotNull(result);
        assertEquals("홍길동", result.getName());
        assertEquals("123456789", result.getIdNumber());
    }

    @Test
    void deleteById() {
        doNothing().when(representativeRepository).deleteById(1L);

        companyRegistrationService.deleteById(1L);

        verify(representativeRepository, times(1)).deleteById(1L);
    }
}