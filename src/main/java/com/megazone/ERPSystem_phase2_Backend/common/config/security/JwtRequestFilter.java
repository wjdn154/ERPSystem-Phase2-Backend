package com.megazone.ERPSystem_phase2_Backend.common.config.security;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Users.UsersService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
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

/**
 * JwtRequestFilter 클래스
 *
 * JWT 인증을 처리하는 필터. 각 요청마다 JWT 토큰을 확인하고 사용자를 인증함.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService customUserDetailsService; // 사용자 인증 정보를 처리하는 서비스
    private final JwtUtil jwtUtil; // JWT 유틸리티 클래스

    /**
     * 생성자
     *
     * @param customUserDetailsService 사용자 인증 서비스를 주입받음
     * @param jwtUtil JWT 유틸리티 클래스 주입
     */
    @Autowired
    public JwtRequestFilter(CustomUserDetailsService customUserDetailsService, JwtUtil jwtUtil) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * JWT 인증 처리
     *
     * @param request HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @param chain 필터 체인
     * @throws ServletException 서블릿 예외
     * @throws IOException 입출력 예외
     */
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

        String userName = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);  // "Bearer " 부분을 제거하고 토큰만 추출
            try {
                userName = jwtUtil.extractUsername(jwt);  // JWT에서 사용자 ID 추출
            } catch (ExpiredJwtException e) {
                System.out.println("ExpiredJwtException 발생: " + e.getMessage());
                request.setAttribute("ExpiredJwtException", e);  // 예외를 request에 저장
                chain.doFilter(request, response);  // 필터 체인을 계속 진행
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 토큰이 유효하고 인증되지 않은 경우에만 실행
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);

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