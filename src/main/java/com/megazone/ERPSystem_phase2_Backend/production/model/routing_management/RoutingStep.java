package com.megazone.ERPSystem_phase2_Backend.production.model.routing_management;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "routing_management_routing_step")
@Table(name = "routing_management_routing_step", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"production_routing_id", "step_order"})
}, indexes = {
        @Index(name = "idx_routing_step_order", columnList = "production_routing_id, stepOrder")
})
@AttributeOverride(name = "id.productionRoutingId", column = @Column(name = "production_routing_id"))
@AttributeOverride(name = "id.processId", column = @Column(name = "process_id"))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoutingStep {

    @EmbeddedId
    private RoutingStepId id; // 복합키를 위한 식별자

    @ManyToOne
    @MapsId("productionRoutingId") // 복합키의 일부인 routingId를 사용하여 연관관계를 맺음
    @JoinColumn(name = "production_routing_id")
    private ProductionRouting productionRouting;

    @ManyToOne
    @MapsId("processId") // 복합키의 일부인 processId를 사용하여 연관관계를 맺음
    @JoinColumn(name = "process_id")
    private ProcessDetails process;

    @Column(name = "step_order")
    private Long stepOrder; // Routing에서의 순서를 정의하는 필드

}


