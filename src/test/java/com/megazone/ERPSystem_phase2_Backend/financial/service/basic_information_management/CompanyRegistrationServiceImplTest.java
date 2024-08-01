package com.megazone.ERPSystem_phase2_Backend.financial.service.basic_information_management;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company_registration.Representative;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.company_registration.Representative.RepresentativeRepository;
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
    private RepresentativeRepository representativeRepository; // Mock 객체로 대체된 RepresentativeRepository

    @InjectMocks
    private CompanyRegistrationServiceImpl companyRegistrationService; // 테스트할 대상 클래스

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Mock 객체 초기화
    }

    /**
     * findByName 테스트 메서드
     * 특정 이름을 가진 Representative를 조회하는 기능을 테스트함
     */
    @Test
    void findByName() {
        Representative representative = new Representative(1L, "홍길동", "123456789", false);
        when(representativeRepository.findByName("홍길동")).thenReturn(Arrays.asList(representative)); // Mock 동작 정의

        List<Representative> result = companyRegistrationService.findByName("홍길동"); // 테스트 실행

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("홍길동", result.get(0).getName());
    }

    /**
     * save 테스트 메서드
     * Representative 객체를 저장하는 기능을 테스트함
     */
    @Test
    void save() {
        Representative representative = new Representative(1L, "홍길동", "123456789", false);
        when(representativeRepository.save(any(Representative.class))).thenReturn(representative); // Mock 동작 정의

        Representative result = companyRegistrationService.save(representative); // 테스트 실행

        assertNotNull(result);
        assertEquals("홍길동", result.getName());
        assertEquals("123456789", result.getIdNumber());
    }

    /**
     * findById 테스트 메서드
     * ID로 Representative 객체를 조회하는 기능을 테스트함
     */
    @Test
    void findById() {
        Representative representative = new Representative(1L, "홍길동", "123456789", false);
        when(representativeRepository.findById(1L)).thenReturn(Optional.of(representative)); // Mock 동작 정의

        Representative result = companyRegistrationService.findById(1L); // 테스트 실행

        assertNotNull(result);
        assertEquals("홍길동", result.getName());
        assertEquals("123456789", result.getIdNumber());
    }

    /**
     * deleteById 테스트 메서드
     * ID로 Representative 객체를 삭제하는 기능을 테스트함
     */
    @Test
    void deleteById() {
        doNothing().when(representativeRepository).deleteById(1L); // Mock 동작 정의

        companyRegistrationService.deleteById(1L); // 테스트 실행

        verify(representativeRepository, times(1)).deleteById(1L); // 메서드 호출 확인
    }
}