package com.megazone.ERPSystem_phase2_Backend.common.config.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 사용자 인증 요청 시 전달되는 데이터를 담는 클래스
 *
 */
@Getter
@Setter
public class AuthRequest {
    private String userName;  // 사용자 ID
    private String password;  // 사용자 비밀번호
    private String userNickname;  // 사용자 닉네임
    private Long companyId;  // 회사 ID
}