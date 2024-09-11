package com.megazone.ERPSystem_phase2_Backend.common.config.aop;

import com.megazone.ERPSystem_phase2_Backend.common.config.multi_tenant.TenantContext;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 헤더에 'X-Tenant-ID' 가 있는 HTTP 요청에서 테넌트 식별자를 설정하고 해제하는 역할을 하는 AOP 클래스임.
 */
@Aspect
@Component
public class TenantContextAspect {

    /**
     * 특정 메서드 실행 전에 'X-Tenant-ID' 헤더에서 테넌트 식별자를 추출하여 설정함.
     */
    @Before("@annotation(org.springframework.web.bind.annotation.RequestMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void setTenantContext() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String tenantId = request.getHeader("X-Tenant-ID");  // HTTP 헤더에서 테넌트 식별자 추출
        if (tenantId != null) {
            TenantContext.setCurrentTenant(tenantId);  // 테넌트 식별자 설정
        } else {
            System.out.println("X-Tenant-ID 헤더가 존재하지 않습니다.");
        }
    }

    /**
     * 메서드 실행 후 테넌트 식별자를 해제함.
     */
    @AfterReturning("@annotation(org.springframework.web.bind.annotation.RequestMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void clearTenantContext() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String tenantId = request.getHeader("X-Tenant-ID");  // HTTP 헤더에서 테넌트 식별자 추출
        if(tenantId != null) {
            TenantContext.clear();  // 테넌트 정보 해제
        }
    }
}