package com.megazone.ERPSystem_phase2_Backend.common.config.security;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * CustomUserDetailsService 클래스
 *
 * Spring Security의 사용자 인증을 위한 UserDetailsService 구현체.
 * 데이터베이스에서 사용자 정보를 로드하여 인증에 필요한 정보를 제공함.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository; // 사용자 정보를 가져오는 저장소

    /**     *
     * @param usersRepository 사용자 정보 저장소
     */
    @Autowired
    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository; // 주입된 UsersRepository로 초기화
    }

    /**
     * 사용자 이름으로 사용자 정보 로드
     *
     * @param userName 사용자 ID
     * @return 사용자 인증 정보 (UserDetails)
     * @throws UsernameNotFoundException 사용자를 찾지 못했을 경우 예외 발생
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Users user = usersRepository.findByUserName(userName) // 사용자 이름으로 검색
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + userName)); // 예외 처리
        return new CustomUserDetails(user); // 사용자 정보를 기반으로 CustomUserDetails 반환
    }
}