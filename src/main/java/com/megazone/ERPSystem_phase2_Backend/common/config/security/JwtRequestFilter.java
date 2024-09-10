package com.megazone.ERPSystem_phase2_Backend.common.config.security;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Users.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // /auth 경로는 필터를 통과시킴
        String path = request.getRequestURI();
        if (path.startsWith("/api/hr/auth/login")) {
            chain.doFilter(request, response);
            return;  // /auth 경로는 더 이상 필터 처리하지 않음
        }

        // Authorization 헤더에서 JWT 토큰 추출
        final String authorizationHeader = request.getHeader("Authorization");

        String userId = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);  // "Bearer " 부분을 제거하고 토큰만 추출
            try {
                userId = jwtUtil.extractUsername(jwt);  // JWT에서 사용자 ID 추출
            } catch (Exception e) {
                e.printStackTrace();  // 예외 발생 시 로그 출력
            }
        }

        // 토큰이 유효하고 인증되지 않은 경우에만 실행
        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userId);

            // 토큰이 유효한지 확인하고, 유효하면 인증 처리
            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());  // 인증 객체 생성
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);  // 인증 객체를 SecurityContext에 설정
            }
        }
        chain.doFilter(request, response);  // 모든 경로에 대해 필터 체인 호출
    }
}