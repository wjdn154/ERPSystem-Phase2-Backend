package com.megazone.ERPSystem_phase2_Backend.Integrated.controller.notification;

import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.Notification;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.dto.UserNotificationDTO;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.dto.UserSubscriptionDTO;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.ModuleType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.PermissionType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.service.notification.NotificationService;
import com.megazone.ERPSystem_phase2_Backend.common.config.multi_tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/get-user-subscription-info")
    public ResponseEntity<UserSubscriptionDTO> getUserSubscriptionInfo(
            @RequestParam("employeeId") Long employeeId,
            @RequestParam("isAdmin") boolean isAdmin) {
        return ResponseEntity.ok(notificationService.getUserSubscriptionInfo(employeeId, isAdmin));
    }

    // SSE 구독
    @GetMapping(value = "/subscribe")
    public SseEmitter subscribe(
            @RequestParam("employeeId") Long employeeId,
            @RequestParam("tenantId") String tenantId,
            @RequestParam("module") ModuleType module,
            @RequestParam("permission") PermissionType permission){

        SseEmitter emitter = notificationService.subscribe(employeeId, tenantId, module, permission);

        try {
            emitter.send(SseEmitter.event().name("subscribe").data("구독이 성공적으로 연결되었습니다."));
        } catch (IOException e) {
            emitter.completeWithError(e);
        }

        return emitter;
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<Void> unsubscribe(@RequestBody Map<String, Long> request) {
        Long employeeId = request.get("employeeId");
        notificationService.removeEmitter(employeeId);
        return ResponseEntity.ok().build();
    }

    // 이력 생성 및 조회
    @PostMapping("/create-notification")
    public ResponseEntity<Object> createNotification(
            @RequestParam("employeeId") Long employeeId,
            @RequestParam("tenantId") String tenantId,
            @RequestParam("module") ModuleType module,
            @RequestParam("permission") PermissionType permission) {

        System.out.println("create-notification : employeeId = " + employeeId);

        List<UserNotificationDTO> notification = notificationService.createAndSearch(employeeId, tenantId, module, permission);
        return ResponseEntity.ok(notification);
    }
}