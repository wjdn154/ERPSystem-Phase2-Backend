package com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification;

import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.ModuleType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.NotificationType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.PermissionType;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_notification")
@Table(name = "user_notification")
public class UserNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) private Users users; // 사용자
    @ManyToOne(optional = false) private Notification notification;

    @Enumerated(EnumType.STRING) private ModuleType module; // 모듈 유형
    @Enumerated(EnumType.STRING) private PermissionType permission; // 권한 유형
    @Enumerated(EnumType.STRING) private NotificationType type; // 알림 유형

    private String content; // 알림 내용
    private LocalDateTime createAt; // 알림 생성 시간
    private LocalDateTime readAt; // 알림 읽음 시간
    private boolean readStatus; // 읽음 상태
}
