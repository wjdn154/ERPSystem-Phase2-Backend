package com.megazone.ERPSystem_phase2_Backend.common.config.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    // 사용자 인증 요청 시 전달되는 사용자 ID와 비밀번호를 담는 클래스.
    private String userName;  // 사용자 ID
    private String password;  // 사용자 비밀번호
    private String userNickname;  // 사용자 닉네임
    private Long CompanyId;  // 회사 ID
}