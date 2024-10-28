package com.megazone.ERPSystem_phase2_Backend.Integrated.model;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.enums.ActivityType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * 최근 활동 기록 엔티티
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "recent_activity")
@Table(name = "recent_activity")
public class RecentActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityType activityType; // 활동 유형 (재무회계, 인사관리, 생산관리, 물류관리)

    @Column(nullable = false) private String activityDescription; // 활동 내용
    @Column(nullable = false) private LocalDateTime activityTime; // 활동 발생 시간
}