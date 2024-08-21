package com.megazone.ERPSystem_phase2_Backend.production.model.routing_management;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "routing_step")
@Table(name = "routing_management_routing_step")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutingStep {

    @EmbeddedId
    private RoutingStepId id; // 복합키를 위한 식별자

    @ManyToOne
    @MapsId("routingId") // 복합키의 일부인 routingId를 사용하여 연관관계를 맺음
    @JoinColumn(name = "routing_id")
    private Routing routing;

    @ManyToOne
    @MapsId("processId") // 복합키의 일부인 processId를 사용하여 연관관계를 맺음
    @JoinColumn(name = "process_id")
    private ProcessDetails process;

    private Long stepOrder; // Routing에서의 순서를 정의하는 필드

}


