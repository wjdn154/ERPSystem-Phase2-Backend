package com.megazone.ERPSystem_phase2_Backend.common.config.aop;

import com.megazone.ERPSystem_phase2_Backend.common.config.multi_tenant.TenantContext;
import com.megazone.ERPSystem_phase2_Backend.common.config.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 헤더의 jwt 토큰에 'X-Tenant-ID' 가 있는 HTTP 요청에서 테넌트 식별자를 설정하고 해제하는 역할을 하는 AOP 클래스임.
 */
@Aspect
@Component
@RequiredArgsConstructor
public class TenantAspect {

    private final JwtUtil jwtUtil;

    /**
     * 요청 전에 JWT 토큰에서 X-Tenant-ID를 추출하고 TenantContext에 테넌트 ID를 설정함
     * @throws Throwable 오류 발생 시 던짐
     */
    @Before("@annotation(org.springframework.web.bind.annotation.RequestMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void setTenantContext() throws Throwable {
        // 요청에서 HttpServletRequest 객체를 얻음
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            // JWT 토큰을 요청 헤더에서 추출함
            String token = jwtUtil.resolveToken(request);
            // 토큰이 있으면 테넌트 ID를 추출하여 설정함
            if (token != null) {
                String tenantId = jwtUtil.extractTenantId(token);
                TenantContext.setCurrentTenant(tenantId);
                // 콘솔에 설정된 테넌트 ID를 출력함
            }
        }
    }

    /**
     * 요청 후에 JWT 토큰에서 X-Tenant-ID가 있으면 TenantContext를 비움
     * @throws Throwable 오류 발생 시 던짐
     */
    @Before("@annotation(org.springframework.web.bind.annotation.RequestMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void clearTenantContext() throws Throwable {
        // 요청에서 HttpServletRequest 객체를 얻음
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            // JWT 토큰을 요청 헤더에서 추출함
            String token = jwtUtil.resolveToken(request);
            // 토큰이 있으면 TenantContext를 클리어함
            if (token != null) {
                TenantContext.clear();
                // 콘솔에 TenantContext 클리어 완료 메시지를 출력함
            }
        }
    }
}