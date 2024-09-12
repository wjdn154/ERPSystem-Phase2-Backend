package com.megazone.ERPSystem_phase2_Backend.production.model.production_schedule.common_scheduling;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "common_scheduling_shift_type")
@Table(name = "common_scheduling_shift_type")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ShiftType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;  // 교대 근무 이름 (예: 주간, 야간, 4조 3교대 등)

    @Column(nullable = false)
    private String description;  // 교대 근무에 대한 설명

    @Column(nullable = false)
    private Double duration;  // 근무 시간 (시간 단위)

    @OneToMany(mappedBy = "shiftType")
    private List<WorkerAssignment> workerAssignments;

    @Column(nullable = false)
    private Boolean isUsed;
}

/**
 *     DAY_SHIFT("주간", 8),       // 주간 근무 (8시간)
 *     NIGHT_SHIFT("야간", 8),     // 야간 근무 (8시간)
 *     MORNING_SHIFT("오전", 8),   // 오전 근무 (8시간)
 *     AFTERNOON_SHIFT("오후", 8), // 오후 근무 (8시간)
 *     TWELVE_HOUR_DAY("주간 12시간", 12), // 12시간 주간 근무
 *     TWELVE_HOUR_NIGHT("야간 12시간", 12), // 12시간 야간 근무
 *     FOUR_GROUP_THREE_SHIFT("4조 3교대", 8), // 4개조 3교대 근무
 *     THREE_GROUP_TWO_SHIFT("3조 2교대", 12), // 3개조 2교대 근무
 *     FOUR_GROUP_TWO_SHIFT("4조 2교대", 12); // 4개조 2교대 근무
 */