package com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.dto;

import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.Notification;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.UserNotification;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.ModuleType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.NotificationType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.PermissionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserNotificationSearchDTO {
    private Long userId;
    private Notification notification;
    private String module;
    private String permission;
    private String type;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime readAt;
    private boolean readStatus;
}
