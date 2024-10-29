package com.megazone.ERPSystem_phase2_Backend.Integrated.service.notification;

import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.Notification;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.dto.UserSubscriptionDTO;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.ModuleType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.PermissionType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationService {
    SseEmitter subscribe(Long employeeId, String tenantId, ModuleType module, PermissionType permission); // 사용자 구독 메서드
    Notification createAndSendNotification(String content, ModuleType module, PermissionType permission); // 알림 생성 및 전송 메서드
    void sendNotification(Notification notification, String tenantId); // 전체 사용자에게 알림 전송
    UserSubscriptionDTO getUserSubscriptionInfo(Long employeeId, boolean isAdmin);
}
