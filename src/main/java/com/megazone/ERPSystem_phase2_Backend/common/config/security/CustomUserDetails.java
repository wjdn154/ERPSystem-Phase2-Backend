package com.megazone.ERPSystem_phase2_Backend.common.config.security;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * CustomUserDetails 클래스
 *
 * Spring Security에서 사용자 인증 정보를 담기 위한 UserDetails 인터페이스 구현체.
 * 사용자의 인증 및 권한 관련 정보를 제공함.
 */
public class CustomUserDetails implements UserDetails {

    private final Users user; // 사용자 정보를 담는 Users 객체

    /**
     * 생성자
     *
     * @param user 사용자 정보 객체
     */
    public CustomUserDetails(Users user) {
        this.user = user;
    }

    /**
     * 사용자 권한 목록 반환
     *
     * @return GrantedAuthority 목록 (사용자 권한)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>(); // 권한 목록을 저장할 리스트 생성
        // 권한이 추가될 경우 리스트에 권한을 추가할 수 있음.
        return authorities; // 사용자의 권한 목록 반환
    }

    /**
     * 사용자 비밀번호 반환
     *
     * @return 사용자 비밀번호 (String)
     */
    @Override
    public String getPassword() {
        return user.getPassword(); // 사용자의 패스워드 반환
    }

    /**
     * 사용자 ID 반환
     *
     * @return 사용자 ID (String)
     */
    @Override
    public String getUsername() {
        return user.getUserName(); // 사용자의 ID 반환
    }

    /**
     * 계정이 만료되지 않았는지 확인
     *
     * @return 계정이 만료되지 않음 (true)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정이 만료되지 않았음을 반환
    }

    /**
     * 계정이 잠기지 않았는지 확인
     *
     * @return 계정이 잠기지 않음 (true)
     */
    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정이 잠기지 않았음을 반환
    }

    /**
     * 자격 증명이 만료되지 않았는지 확인
     *
     * @return 자격 증명이 만료되지 않음 (true)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명이 만료되지 않았음을 반환
    }

    /**
     * 계정이 활성화되어 있는지 확인
     *
     * @return 계정이 활성화되어 있음 (true)
     */
    @Override
    public boolean isEnabled() {
        return true; // 계정이 활성화되어 있음을 반환
    }
}