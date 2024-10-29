package com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_notification")
@Table(name = "user_notification")
public class UserNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne private Users users; // 사용자
    @ManyToOne private Notification notification; // 알림
    private boolean readStatus; // 읽음 상태
    private LocalDateTime receivedAt; // 알림 수신 시간
    private LocalDateTime readAt; // 알림 읽음 시간
}
