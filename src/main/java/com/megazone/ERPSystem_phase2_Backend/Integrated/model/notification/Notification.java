package com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification;

import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.ModuleType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.NotificationType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.PermissionType;
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
@Entity(name = "notification")
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING) private ModuleType module; // 모듈 유형
    @Enumerated(EnumType.STRING) private PermissionType permission; // 권한 유형
    @Enumerated(EnumType.STRING) private NotificationType type; // 알림 유형

    private String content; // 알림 내용
    private LocalDateTime createAt; // 알림 생성 시간
}
