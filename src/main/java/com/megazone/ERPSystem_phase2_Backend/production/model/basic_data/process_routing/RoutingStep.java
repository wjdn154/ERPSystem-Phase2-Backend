package com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "process_routing_routing_step")
@Table(name = "process_routing_routing_step", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"process_routing_id", "step_order"})
}, indexes = {
        @Index(name = "idx_routing_step_order", columnList = "process_routing_id, stepOrder")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoutingStep {

    @EmbeddedId
    private RoutingStepId id; // 복합키를 위한 식별자

    @ManyToOne
    @MapsId("processRoutingId") // 복합키의 일부인 routingId를 사용하여 연관관계를 맺음
    @JoinColumn(name = "process_routing_id")
    private ProcessRouting processRouting;

    @ManyToOne
    @MapsId("processId") // 복합키의 일부인 processId를 사용하여 연관관계를 맺음
    @JoinColumn(name = "process_id")
    private ProcessDetails process;

    @Column(name = "step_order")
    private Long stepOrder; // Routing에서의 순서를 정의하는 필드

}


