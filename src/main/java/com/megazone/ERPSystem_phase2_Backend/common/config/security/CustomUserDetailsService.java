package com.megazone.ERPSystem_phase2_Backend.common.config.security;

import com.megazone.ERPSystem_phase2_Backend.common.config.multi_tenant.TenantContext;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * CustomUserDetailsService 클래스
 *
 * Spring Security의 사용자 인증을 위한 UserDetailsService 구현체.
 * 데이터베이스에서 사용자 정보를 로드하여 인증에 필요한 정보를 제공함.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository; // 사용자 정보를 가져오는 저장소
    private final JwtUtil jwtUtil;

    /**
     * 사용자 이름으로 사용자 정보 로드
     *
     * @param userName 사용자 ID
     * @return 사용자 인증 정보 (UserDetails)
     * @throws UsernameNotFoundException 사용자를 찾지 못했을 경우 예외 발생
     */
    @Override
    public UserDetails loadUserByUsername(String userName) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String token = jwtUtil.resolveToken(request);
        System.out.println("token = " + token);

        if (token != null) {
            String tenantId = jwtUtil.extractTenantId(token);
            TenantContext.setCurrentTenant(tenantId);
            // 콘솔에 설정된 테넌트 ID를 출력함
            System.out.println("연결된 테넌트 스키마: " + tenantId);
        }

        Users user = usersRepository.findByUserName(userName) // 사용자 이름으로 검색
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + userName)); // 예외 처리

        TenantContext.clear();
        return new CustomUserDetails(user); // 사용자 정보를 기반으로 CustomUserDetails 반환
    }
}